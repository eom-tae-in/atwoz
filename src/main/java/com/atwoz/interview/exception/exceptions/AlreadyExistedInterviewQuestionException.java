package com.atwoz.interview.exception.exceptions;

public class AlreadyExistedInterviewQuestionException extends RuntimeException {

    public AlreadyExistedInterviewQuestionException() {
        super("이미 등록된 인터뷰 질문 내용 입니다.");
    }
}
