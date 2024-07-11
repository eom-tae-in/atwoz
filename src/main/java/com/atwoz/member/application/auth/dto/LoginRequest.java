package com.atwoz.member.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "인증 서버가 정해지지 않았습니다.")
        String provider,

        @NotBlank(message = "인증 코드가 비었습니다.")
        String code,

        @NotBlank(message = "FCM 토큰이 비었습니다.")
        String token
) {
}
