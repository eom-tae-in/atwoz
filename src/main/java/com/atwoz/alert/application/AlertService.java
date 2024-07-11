package com.atwoz.alert.application;

import com.atwoz.alert.application.dto.CreateAlertRequest;
import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AlertService {

    private final AlertManager alertManager;

    public void sendAlert(final CreateAlertRequest request) {
        Alert alert = Alert.createWith(request.group(), request.title(), request.body(), request.token());
        alertManager.send(alert);
    }
}
