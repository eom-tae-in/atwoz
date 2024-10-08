package com.atwoz.member.exception.exceptions.member;

public class MemberContactInfoAlreadyExistedException extends RuntimeException {

    public MemberContactInfoAlreadyExistedException() {
        super("회원 연락처 정보가 이미 존재합니다.");
    }
}
