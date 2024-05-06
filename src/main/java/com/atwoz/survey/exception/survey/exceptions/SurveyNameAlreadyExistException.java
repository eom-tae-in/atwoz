package com.atwoz.survey.exception.survey.exceptions;

public class SurveyNameAlreadyExistException extends RuntimeException {

    public SurveyNameAlreadyExistException() {
        super("이미 존재하는 연애고사 과목입니다.");
    }
}
