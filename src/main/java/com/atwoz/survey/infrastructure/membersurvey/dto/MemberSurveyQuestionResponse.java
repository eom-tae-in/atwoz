package com.atwoz.survey.infrastructure.membersurvey.dto;

public record MemberSurveyQuestionResponse(
        Long questionId,
        Integer answerNumber
) {
}
