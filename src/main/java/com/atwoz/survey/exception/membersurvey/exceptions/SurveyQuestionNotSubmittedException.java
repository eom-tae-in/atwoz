package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveyQuestionNotSubmittedException extends RuntimeException {

    public SurveyQuestionNotSubmittedException() {
        super("제출에 작성되지 않은 질문이 있습니다.");
    }
}
