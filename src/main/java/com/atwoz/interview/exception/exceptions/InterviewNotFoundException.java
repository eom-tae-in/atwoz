package com.atwoz.interview.exception.exceptions;

public class InterviewNotFoundException extends RuntimeException {

    public InterviewNotFoundException() {
        super("인터뷰를 찾을 수 없습니다.");
    }
}
