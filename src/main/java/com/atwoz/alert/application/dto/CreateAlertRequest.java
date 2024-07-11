package com.atwoz.alert.application.dto;

import jakarta.validation.constraints.NotEmpty;

public record CreateAlertRequest(
        @NotEmpty(message = "알림 발송 그룹이 있어야 합니다.")
        String group,
        @NotEmpty(message = "대상 토큰이 있어야 합니다.")
        String token,
        @NotEmpty(message = "알림 제목이 있어야 합니다.")
        String title,
        String body
) {
}
