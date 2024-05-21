package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveyAlreadySubmittedException extends RuntimeException {

    public SurveyAlreadySubmittedException() {
        super("이미 제출한 연애고사 과목입니다.");
    }
}
