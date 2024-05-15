package com.atwoz.survey.infrastructure.membersurvey.dto;

public record MemberSurveyResponse(
        Long memberId,
        Long surveyId,
        Long questionId,
        Integer answerNumber) {
}
