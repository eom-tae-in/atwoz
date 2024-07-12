package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertManager;

public class AlertFakeManager implements AlertManager {

    @Override
    public void send(final Alert alert, final String sender, final String token) {
        return;
    }
}
