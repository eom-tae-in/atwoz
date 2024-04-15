package com.atwoz.member.exception.exceptions.member;

public class MemberNicknameAlreadyExistedException extends RuntimeException {

    public MemberNicknameAlreadyExistedException() {
        super("이미 존재하는 닉네임입니다.");
    }
}
