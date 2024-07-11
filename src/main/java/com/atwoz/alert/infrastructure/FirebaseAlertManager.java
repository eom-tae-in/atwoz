package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FirebaseAlertManager implements AlertManager {

    private static final String GROUP = "group";
    private static final String SENDER = "sender";
    private static final String CREATED_TIME = "created_at";
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void send(final Alert alert, final String sender, final String token) {
        LocalDateTime createdAt = alert.getCreatedAt();
        String time = createdAt.format(DateTimeFormatter.ofPattern(TIME_FORMAT));

        Notification firebaseNotification = Notification.builder()
                .setTitle(alert.getTitle())
                .setBody(alert.getBody())
                .build();
        Message firebaseMessage = Message.builder()
                .setToken(token)
                .putData(GROUP, alert.getGroup())
                .setNotification(firebaseNotification)
                .putData(SENDER, sender)
                .putData(CREATED_TIME, time)
                .build();
        FirebaseMessaging firebase = FirebaseMessaging.getInstance();
        firebase.sendAsync(firebaseMessage);
    }
}
