package com.atwoz.alert.application;

import com.atwoz.alert.application.event.AlertCreatedEvent;
import com.atwoz.alert.application.event.AlertTokenCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlertEventHandler {

    private final AlertService alertService;

    @EventListener
    public void sendAlertCreatedEvent(final AlertCreatedEvent event) {
        alertService.sendAlert(event.group(), event.title(), event.body(), event.sender(), event.receiverId());
    }

    @EventListener
    public void sendAlertTokenCreatedEvent(final AlertTokenCreatedEvent event) {
        alertService.saveToken(event.id(), event.token());
    }
}
