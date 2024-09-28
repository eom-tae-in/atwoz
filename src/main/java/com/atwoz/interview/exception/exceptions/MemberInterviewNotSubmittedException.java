package com.atwoz.interview.exception.exceptions;

public class MemberInterviewNotSubmittedException extends RuntimeException {

    public MemberInterviewNotSubmittedException() {
        super("아직 답변된 회원 인터뷰가 없습니다.");
    }
}
