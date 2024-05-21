package com.atwoz.survey.domain.survey.dto;

import com.atwoz.survey.application.membersurvey.dto.SurveyQuestionSubmitRequest;

public record SurveyQuestionComparisonRequest(
        Long questionId,
        Integer answerNumber
) {

    public static SurveyQuestionComparisonRequest from(final SurveyQuestionSubmitRequest request) {
        return new SurveyQuestionComparisonRequest(request.questionId(), request.answerNumber());
    }
}
