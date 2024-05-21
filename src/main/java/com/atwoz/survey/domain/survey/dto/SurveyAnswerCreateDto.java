package com.atwoz.survey.domain.survey.dto;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;

public record SurveyAnswerCreateDto(
        Integer number,
        String answer
) {

    public static SurveyAnswerCreateDto from(final SurveyAnswerCreateRequest request) {
        return new SurveyAnswerCreateDto(request.number(), request.answer());
    }
}
