package com.atwoz.interview.exception.exceptions;

public class InterviewTypeNotFoundException extends RuntimeException {

    public InterviewTypeNotFoundException() {
        super("등록되지 않은 인터뷰 타입입니다.");
    }
}
