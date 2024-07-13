package com.atwoz.alert.application;

import com.atwoz.alert.application.event.AlertCreatedEvent;
import com.atwoz.alert.application.event.AlertTokenCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class AlertEventHandler {

    private final AlertService alertService;

    @TransactionalEventListener
    public void sendAlertCreatedEvent(final AlertCreatedEvent event) {
        alertService.sendAlert(event.group(), event.title(), event.body(), event.sender(), event.receiverId());
    }

    @TransactionalEventListener
    public void sendAlertTokenCreatedEvent(final AlertTokenCreatedEvent event) {
        alertService.saveToken(event.id(), event.token());
    }
}
