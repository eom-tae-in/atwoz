package com.atwoz.survey.exception.membersurvey.exceptions;

public class RequiredSurveyNotSubmittedException extends RuntimeException {

    public RequiredSurveyNotSubmittedException() {
        super("필수 연애고사 과목을 제출하지 않았습니다.");
    }
}
