package com.atwoz.survey.domain.membersurvey.dto;

public record SurveyQuestionSubmitCreateDto(
        Long questionId,
        Integer answerNumber
) {
}
