package com.atwoz.admin.application.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record AdminSignUpRequest(
        @NotBlank(message = "이메일을 입력해주세요")
        String email,

        @NotBlank(message = "비밀번호를 입력해주세요")
        String password,

        @NotBlank(message = "비밀번호 확인을 위해 다시한번 입력해주세요")
        String confirmPassword,

        @NotBlank(message = "이름을 입력해주세요.")
        String name,

        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNumber
) {
}
