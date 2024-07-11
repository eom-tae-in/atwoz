package com.atwoz.alert.domain;

public interface AlertTokenRepository {

    void saveToken(Long id, String token);
    String getToken(Long id);
    void deleteToken(Long id);
    boolean hasKey(Long id);
}
