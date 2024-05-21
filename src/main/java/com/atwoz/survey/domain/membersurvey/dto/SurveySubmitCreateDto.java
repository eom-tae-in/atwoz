package com.atwoz.survey.domain.membersurvey.dto;

import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;

import java.util.List;

public record SurveySubmitCreateDto(
        Long surveyId,
        List<SurveyQuestionSubmitCreateDto> questions
) {

    public static SurveySubmitCreateDto from(final SurveySubmitRequest request) {
        List<SurveyQuestionSubmitCreateDto> questions = request.questions()
                .stream()
                .map(question -> new SurveyQuestionSubmitCreateDto(question.questionId(), question.answerNumber()))
                .toList();

        return new SurveySubmitCreateDto(request.surveyId(), questions);
    }
}
