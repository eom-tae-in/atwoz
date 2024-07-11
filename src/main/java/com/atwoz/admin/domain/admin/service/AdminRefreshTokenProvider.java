package com.atwoz.admin.domain.admin.service;

public interface AdminRefreshTokenProvider {

    String createRefreshToken(String email);
}
