package com.atwoz.survey.ui.survey.dto;

import com.atwoz.survey.domain.survey.SurveyAnswer;
import com.atwoz.survey.domain.survey.SurveyQuestion;

import java.util.List;

public record SurveyQuestionCreateResponse(
        Long questionId,
        List<Integer> answerNumbers
) {

    public static SurveyQuestionCreateResponse from(final SurveyQuestion question) {
        List<Integer> answerNumbers = question.getAnswers()
                .stream()
                .map(SurveyAnswer::getNumber)
                .toList();
        return new SurveyQuestionCreateResponse(question.getId(), answerNumbers);
    }
}
