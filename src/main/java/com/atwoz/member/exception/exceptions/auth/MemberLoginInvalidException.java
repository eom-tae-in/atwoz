package com.atwoz.member.exception.exceptions.auth;

public class MemberLoginInvalidException extends RuntimeException {

    public MemberLoginInvalidException() {
        super("로그인 정보를 찾을 수 없습니다.");
    }
}
