package com.atwoz.survey.infrastructure.membersurvey.dto;

public record MemberSurveyAnswerResponse(
        Long memberId,
        Long surveyId,
        Long questionId,
        Integer answerNumber) {
}
