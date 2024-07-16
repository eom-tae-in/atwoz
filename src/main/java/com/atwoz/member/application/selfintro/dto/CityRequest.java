package com.atwoz.member.application.selfintro.dto;

import jakarta.validation.constraints.NotBlank;

public record CityRequest(
        @NotBlank(message = "선호하는 지역 정보를 입력해주세요.")
        String city
) {
}
