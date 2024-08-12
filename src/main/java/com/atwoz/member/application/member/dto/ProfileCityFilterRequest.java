package com.atwoz.member.application.member.dto;

import jakarta.validation.constraints.NotBlank;

public record ProfileCityFilterRequest(
        @NotBlank(message = "선호 지역을 입력해주세요.")
        String city
) {
}
