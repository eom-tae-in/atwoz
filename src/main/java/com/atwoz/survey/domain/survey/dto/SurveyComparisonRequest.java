package com.atwoz.survey.domain.survey.dto;

import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;

import java.util.List;

public record SurveyComparisonRequest(
        List<SurveyQuestionComparisonRequest> questions
) {

    public static SurveyComparisonRequest from(final SurveySubmitRequest request) {
        List<SurveyQuestionComparisonRequest> questions = request.questions()
                .stream()
                .map(SurveyQuestionComparisonRequest::from)
                .toList();
        return new SurveyComparisonRequest(questions);
    }
}
