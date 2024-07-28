package com.atwoz.alert.infrastructure;

import com.atwoz.alert.application.AlertManager;
import com.atwoz.alert.domain.Alert;
import com.google.api.core.ApiFuture;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MessagingErrorCode;
import com.google.firebase.messaging.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@Slf4j
@Component
public class FirebaseAlertManager implements AlertManager {

    private static final String EXECUTOR = "alertCallbackExecutor";
    private static final String GROUP = "group";
    private static final String SENDER = "sender";
    private static final String CREATED_TIME = "created_at";
    private static final String BODY = "body";
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String THREAD_PROBLEM = "FCM 스레드에서 문제가 발생했습니다.";
    private static final String ALERT_FAIL = "알림 전송 실패";
    private static final String ALERT_RETRY_FAIL = "알림 재전송 실패";
    private static final String ALERT_THREAD_WAIT_FAIL = "알림 재전송 스레드 대기 예외";
    private static final int RETRY_MAX = 3;
    private static final int[] RETRY_TIMES = {1000, 2000, 4000};

    private final Executor executor;

    public FirebaseAlertManager(@Qualifier(value = EXECUTOR) final Executor executor) {
        this.executor = executor;
    }

    @Override
    public void send(final Alert alert, final String sender, final String token) {
        Message firebaseMessage = createAlertMessage(alert, sender, token);
        FirebaseMessaging firebase = FirebaseMessaging.getInstance();
        ApiFuture<String> process = firebase.sendAsync(firebaseMessage);

        Runnable task = () -> logAlertResult(process, firebaseMessage);
        process.addListener(task, executor);
    }

    private Message createAlertMessage(final Alert alert, final String sender, final String token) {
        LocalDateTime createdAt = alert.getCreatedAt();
        String time = createdAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT));

        Notification firebaseNotification = Notification.builder()
                .setTitle(alert.getTitle())
                .build();

        return Message.builder()
                .setToken(token)
                .putData(GROUP, alert.getGroup())
                .setNotification(firebaseNotification)
                .putData(SENDER, sender)
                .putData(CREATED_TIME, time)
                .putData(BODY, alert.getBody())
                .build();
    }

    private void logAlertResult(final ApiFuture<String> process, final Message message) {
        try {
            process.get();
        } catch (InterruptedException exception) {
            log.error(THREAD_PROBLEM);
        } catch (ExecutionException exception) {
            log.error(ALERT_FAIL);
            retryAlert(message, exception);
        }
    }

    private void retryAlert(final Message message, final ExecutionException exception) {
        if (exception.getCause() instanceof FirebaseMessagingException e) {
            MessagingErrorCode errorCode = e.getMessagingErrorCode();
            if (!isRetryErrorCode(errorCode)) {
                return;
            }
            retryInThreeTimes(message);
        }
    }

    private boolean isRetryErrorCode(final MessagingErrorCode errorCode) {
        return errorCode.equals(MessagingErrorCode.INTERNAL) || errorCode.equals(MessagingErrorCode.UNAVAILABLE);
    }

    private void retryInThreeTimes(final Message message) {
        int retryCount = 0;
        while (retryCount < RETRY_MAX) {
            if (!shouldRetry(message, retryCount)) {
                break;
            }
            retryCount++;
        }

        if (retryCount == RETRY_MAX) {
            log.error(ALERT_RETRY_FAIL);
        }
    }

    private boolean shouldRetry(final Message message, final int retryCount) {
        wait(retryCount);
        FirebaseMessaging firebase = FirebaseMessaging.getInstance();
        try {
            firebase.sendAsync(message).get();
        } catch (Exception exception) {
            if (exception.getCause() instanceof FirebaseMessagingException e) {
                MessagingErrorCode errorCode = e.getMessagingErrorCode();
                if (isRetryErrorCode(errorCode)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void wait(final int retryCount) {
        try {
            Thread.sleep(RETRY_TIMES[retryCount]);
        } catch (InterruptedException exception) {
            log.error(ALERT_THREAD_WAIT_FAIL);
        }
    }
}
