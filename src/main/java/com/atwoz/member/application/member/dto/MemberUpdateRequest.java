package com.atwoz.member.application.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberUpdateRequest(
        @Valid
        @NotNull(message = "프로필 정보를 입력해주세요.")
        ProfileUpdateRequest profileUpdateRequest,

        @NotBlank(message = "닉네입을 입력해주세요.")
        String nickname
) {
}
