package com.atwoz.survey.exception.survey.exceptions;

public class SurveyQuestionDuplicatedException extends RuntimeException {

    public SurveyQuestionDuplicatedException() {
        super("한 과목에 중복된 질문이 들어갈 수 없습니다.");
    }
}
