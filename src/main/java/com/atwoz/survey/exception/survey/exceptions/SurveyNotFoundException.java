package com.atwoz.survey.exception.survey.exceptions;

public class SurveyNotFoundException extends RuntimeException {

    public SurveyNotFoundException() {
        super("해당 연애고사 과목이 존재하지 않습니다.");
    }
}
