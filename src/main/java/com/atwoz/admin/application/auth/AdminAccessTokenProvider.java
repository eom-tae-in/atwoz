package com.atwoz.admin.application.auth;

public interface AdminAccessTokenProvider {

    String createAccessToken(Long id);
}
