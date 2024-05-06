package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveyAnswerInvalidSubmitException extends RuntimeException {

    public SurveyAnswerInvalidSubmitException() {
        super("제출된 답변이 유효하지 않습니다.");
    }
}
