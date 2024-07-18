package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.domain.AlertTokenRepository;
import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.exception.exceptions.AlertNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class AlertService {

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

    public Alert readAlert(final Long memberId, final Long id) {
        Alert alert = alertRepository.findByMemberIdAndId(memberId, id)
                .orElseThrow(AlertNotFoundException::new);
        alert.read();
        return alert;
    }
}
