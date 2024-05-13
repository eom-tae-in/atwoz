package com.atwoz.survey.application.membersurvey.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SurveySubmitRequest(
        @NotNull(message = "응시할 연애고사 과목 id가 작성되어야 합니다.")
        Long surveyId,
        @NotNull(message = "응시할 연애고사 질문이 작성되어야 합니다.")
        List<SurveyQuestionSubmitRequest> questions
) {
}
