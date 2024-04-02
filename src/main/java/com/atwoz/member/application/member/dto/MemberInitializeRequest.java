package com.atwoz.member.application.member.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberInitializeRequest(
        @Valid
        @NotNull(message = "프로필 정보를 입력해주세요")
        ProfileInitializeRequest profileInitializeRequest,

        @NotBlank(message = "닉네입을 입력해주세요.")
        String nickname,

        String recommender
) {
}

