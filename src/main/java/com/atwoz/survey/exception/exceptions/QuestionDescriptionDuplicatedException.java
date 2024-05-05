package com.atwoz.survey.exception.exceptions;

public class QuestionDescriptionDuplicatedException extends RuntimeException {

    public QuestionDescriptionDuplicatedException() {
        super("한 과목에 중복된 질문이 들어갈 수 없습니다.");
    }
}
