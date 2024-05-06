package com.atwoz.survey.exception.survey.exceptions;

public class SurveyAnswerDuplicatedException extends RuntimeException {

    public SurveyAnswerDuplicatedException() {
        super("중복된 답변 문항이 있습니다.");
    }
}
