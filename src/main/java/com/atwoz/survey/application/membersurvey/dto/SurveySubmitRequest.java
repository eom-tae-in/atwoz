package com.atwoz.survey.application.membersurvey.dto;

import java.util.List;

public record SurveySubmitRequest(
        Long surveyId,
        List<SurveyQuestionSubmitRequest> questions
) {
}
