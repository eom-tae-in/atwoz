package com.atwoz.alert.application;

import com.atwoz.alert.domain.Alert;

public interface AlertManager {

    void send(Alert alert, String sender, String token);
}
