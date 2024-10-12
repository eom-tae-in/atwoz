package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberAccountStatusPatchRequest(
        @NotBlank(message = "회원의 계정 상태를 입력해주세요")
        String status
) {
}
