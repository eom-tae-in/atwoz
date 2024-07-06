package com.atwoz.admin.domain.admin;

public interface AdminTokenProvider {

    String createAccessToken(Long id);

    String createRefreshToken(Long id);

    <T> T extract(String token, String claimName, Class<T> classType);
}
