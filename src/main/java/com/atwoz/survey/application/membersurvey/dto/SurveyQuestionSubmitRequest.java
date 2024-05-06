package com.atwoz.survey.application.membersurvey.dto;

public record SurveyQuestionSubmitRequest(
        Long questionId,
        Integer answerNumber
) {
}
