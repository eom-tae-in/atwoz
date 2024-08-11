package com.atwoz.member.application.member.profile.style.dto;

import jakarta.validation.constraints.NotBlank;

public record StyleUpdateRequest(
        @NotBlank(message = "스타일 이름을 입력해주세요.")
        String name,

        @NotBlank(message = "스타일 코드를 입력해주세요,")
        String code
) {
}
