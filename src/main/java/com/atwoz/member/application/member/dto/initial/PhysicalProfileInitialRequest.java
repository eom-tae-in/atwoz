package com.atwoz.member.application.member.dto.initial;

import jakarta.validation.constraints.NotNull;

public record PhysicalProfileInitialRequest(
        @NotNull(message = "출생년도를 작성해주세요.")
        Integer birthYear,

        @NotNull(message = "키를 입력해주세요.")
        Integer height
) {
}
