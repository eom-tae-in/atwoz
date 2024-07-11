package com.atwoz.admin.application.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AdminSignUpRequest(
        @NotBlank(message = "이메일을 입력해주세요")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,

        @NotBlank(message = "비밀번호 확인을 위해 다시한번 입력해주세요")
        String confirmPassword,

        @Valid
        @NotNull(message = "관리자 프로필 정보를 입력해주세요")
        AdminProfileSignUpRequest adminProfileSignUpRequest
) {
}
