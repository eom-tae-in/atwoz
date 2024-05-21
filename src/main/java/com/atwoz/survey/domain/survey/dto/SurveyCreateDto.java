package com.atwoz.survey.domain.survey.dto;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;

import java.util.List;

public record SurveyCreateDto(
        String name,
        Boolean required,
        List<SurveyQuestionCreateDto> questions
) {

    public static SurveyCreateDto from(final SurveyCreateRequest request) {
        List<SurveyQuestionCreateDto> questions = request.questions()
                .stream()
                .map(SurveyQuestionCreateDto::from)
                .toList();

        return new SurveyCreateDto(request.name(), request.required(), questions);
    }
}
