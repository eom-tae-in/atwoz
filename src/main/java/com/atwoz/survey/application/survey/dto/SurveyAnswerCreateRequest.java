package com.atwoz.survey.application.survey.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SurveyAnswerCreateRequest(
        @NotNull(message = "답변 번호가 작성되어야 합니다. (ex: 1)")
        Integer number,

        @NotBlank(message = "답변 내용이 작성되어야 합니다. (ex: 무계획 여행)")
        String answer
) {

    public static SurveyAnswerCreateRequest of(final Integer number, final String answer) {
        return new SurveyAnswerCreateRequest(number, answer);
    }
}
