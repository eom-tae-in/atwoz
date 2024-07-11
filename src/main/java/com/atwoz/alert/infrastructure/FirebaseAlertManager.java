package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Component;

@Component
public class FirebaseAlertManager implements AlertManager {

    private static final String GROUP = "group";

    @Override
    public void send(final Alert alert) {
        Notification firebaseNotification = Notification.builder()
                .setTitle(alert.getTitle())
                .setBody(alert.getBody())
                .build();
        Message firebaseMessage = Message.builder()
                .setToken(alert.getToken())
                .putData(GROUP, alert.getGroup())
                .setNotification(firebaseNotification)
                .build();
        FirebaseMessaging firebase = FirebaseMessaging.getInstance();
        firebase.sendAsync(firebaseMessage);
    }
}
