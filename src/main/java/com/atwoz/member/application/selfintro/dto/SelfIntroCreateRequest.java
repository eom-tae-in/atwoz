package com.atwoz.member.application.selfintro.dto;

import jakarta.validation.constraints.NotBlank;

public record SelfIntroCreateRequest(

        @NotBlank(message = "소개글을 입력해주세요")
        String content
) {
}
