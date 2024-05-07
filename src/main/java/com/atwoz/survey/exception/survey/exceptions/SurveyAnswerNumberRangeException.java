package com.atwoz.survey.exception.survey.exceptions;

public class SurveyAnswerNumberRangeException extends RuntimeException {

    public SurveyAnswerNumberRangeException() {
        super("설문 답변 번호는 자연수여야 합니다.");
    }
}
