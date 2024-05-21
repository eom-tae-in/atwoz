package com.atwoz.survey.domain.survey.dto;

import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;

import java.util.List;

public record SurveyQuestionCreateDto(
        String description,
        List<SurveyAnswerCreateDto> answers
) {

    public static SurveyQuestionCreateDto from(final SurveyQuestionCreateRequest request) {
        List<SurveyAnswerCreateDto> answers = request.answers()
                .stream()
                .map(SurveyAnswerCreateDto::from)
                .toList();

        return new SurveyQuestionCreateDto(request.description(), answers);
    }
}
