package com.atwoz.admin.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminProfileSignUpRequest(
        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber
) {
}
