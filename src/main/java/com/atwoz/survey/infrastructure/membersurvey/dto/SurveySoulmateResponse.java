package com.atwoz.survey.infrastructure.membersurvey.dto;

public record SurveySoulmateResponse(
        Long id,
        String nickname,
        String city,
        String sector,
        Integer age
) {
}
