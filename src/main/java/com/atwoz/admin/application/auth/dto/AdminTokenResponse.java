package com.atwoz.admin.application.auth.dto;

public record AdminTokenResponse(
        String accessToken,
        String refreshToken
) {
}
