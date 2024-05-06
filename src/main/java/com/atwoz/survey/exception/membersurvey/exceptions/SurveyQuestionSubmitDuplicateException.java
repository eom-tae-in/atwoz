package com.atwoz.survey.exception.membersurvey.exceptions;

public class SurveyQuestionSubmitDuplicateException extends RuntimeException {

    public SurveyQuestionSubmitDuplicateException() {
        super("같은 id의 질문을 제출할 수 없습니다.");
    }
}
