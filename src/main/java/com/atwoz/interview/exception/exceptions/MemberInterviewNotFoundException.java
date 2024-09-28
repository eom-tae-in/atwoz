package com.atwoz.interview.exception.exceptions;

public class MemberInterviewNotFoundException extends RuntimeException {

    public MemberInterviewNotFoundException() {
        super("등록되지 않은 회원 인터뷰입니다.");
    }
}
