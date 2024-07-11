package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.domain.AlertTokenRepository;
import com.atwoz.alert.domain.vo.AlertGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AlertService {

    private static final String MIDNIGHT = "0 0 0 * * ?";

    private final AlertRepository alertRepository;
    private final AlertTokenRepository tokenRepository;
    private final AlertManager alertManager;

    public void saveToken(final Long id, final String token) {
        tokenRepository.saveToken(id, token);
    }

    public void sendAlert(final AlertGroup group, final String title, final String body, final String sender, final Long receiverId) {
        String token = tokenRepository.getToken(receiverId);
        Alert alert = Alert.createWith(group, title, body, receiverId);
        alertManager.send(alert, sender, token);
        alertRepository.save(alert);
    }

    @Scheduled(cron = MIDNIGHT)
    public void deleteExpiredAlerts() {
        alertRepository.deleteExpiredAlerts();
    }
}
