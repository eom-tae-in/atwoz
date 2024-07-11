package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.domain.vo.AlertGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertManager alertManager;

    public void sendAlert(final AlertGroup group, final String title, final String body, final String sender, final String token) {
        Alert alert = Alert.createWith(group, title, body, sender, token);
        alertManager.send(alert);
        alertRepository.save(alert);
    }
}
