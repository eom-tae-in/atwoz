package com.atwoz.member.exception.exceptions.member;

public class InvalidMemberAccountStatusException extends RuntimeException {

    public InvalidMemberAccountStatusException() {
        super("등록되지 않은 회원의 계정 상태입니다.");
    }
}
