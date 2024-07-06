package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;

public interface AlertManager {

    void send(Alert alert);
}
