package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveySubmitDuplicateException extends RuntimeException {

    public SurveySubmitDuplicateException() {
        super("같은 id의 연애고사 과목을 제출할 수 없습니다.");
    }
}
