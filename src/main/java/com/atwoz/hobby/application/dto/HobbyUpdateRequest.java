package com.atwoz.hobby.application.dto;

import jakarta.validation.constraints.NotBlank;

public record HobbyUpdateRequest(
        @NotBlank(message = "취미 이름을 입력해주세요.")
        String name,

        @NotBlank(message = "취미 코드를 입력해주세요.")
        String code
) {
}
