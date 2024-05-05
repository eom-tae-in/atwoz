package com.atwoz.survey.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SurveyCreateRequest(
        @NotBlank(message = "연애고사 과목 이름이 있어야 합니다.")
        String name,

        @NotNull(message = "연애고사 과목 필수 여부가 있어야 합니다.")
        Boolean required,

        @Valid
        List<SurveyQuestionCreateRequest> questions
) {
}
