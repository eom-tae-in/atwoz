package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveyQuestionSubmitSizeNotMatchException extends RuntimeException {

    public SurveyQuestionSubmitSizeNotMatchException() {
        super("과목의 문제 수와 제출한 문제 수가 일치하지 않습니다.");
    }
}
