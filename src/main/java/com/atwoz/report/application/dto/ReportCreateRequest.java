package com.atwoz.report.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReportCreateRequest(
        @NotNull(message = "프로필 id를 입력해주세요")
        Long reportedUserId,

        @NotBlank(message = "신고 유형을 입력해주세요")
        String reportTypeCode,

        String content
) {
}
