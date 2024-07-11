package com.atwoz.alert.domain;

public interface AlertRepository {

    Alert save(Alert alert);
    void deleteExpiredAlerts();
}
