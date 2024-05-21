package com.atwoz.survey.application.membersurvey.dto;

import jakarta.validation.constraints.NotNull;

public record SurveyQuestionSubmitRequest(
        @NotNull(message = "응시할 연애고사 질문 id가 작성되어야 합니다.")
        Long questionId,
        @NotNull(message = "응시할 연애고사 답변 번호가 작성되어야 합니다.")
        Integer answerNumber
) {
}
