package com.atwoz.member.exception.exceptions.member;

public class MemberAlreadyExistedException extends RuntimeException {

    public MemberAlreadyExistedException() {
        super("이미 존재하는 회원입니다.");
    }
}
