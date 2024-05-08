package com.atwoz.survey.ui.survey.dto;

import com.atwoz.survey.domain.survey.Survey;

import java.util.List;

public record SurveyCreateResponse(
        Long surveyId,
        List<SurveyQuestionCreateResponse> questions
) {

    public static SurveyCreateResponse from(final Survey survey) {
        List<SurveyQuestionCreateResponse> questions = survey.getQuestions()
                .stream()
                .map(SurveyQuestionCreateResponse::from)
                .toList();
        return new SurveyCreateResponse(survey.getId(), questions);
    }
}
