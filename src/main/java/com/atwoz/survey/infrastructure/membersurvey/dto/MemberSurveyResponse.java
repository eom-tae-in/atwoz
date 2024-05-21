package com.atwoz.survey.infrastructure.membersurvey.dto;

import java.util.List;

public record MemberSurveyResponse(
        Long surveyId,
        List<MemberSurveyQuestionResponse> questions
) {
}
