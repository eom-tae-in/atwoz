package com.atwoz.profile.exception.exceptions;

public class UnauthorizedProfileModificationException extends RuntimeException {

    public UnauthorizedProfileModificationException() {
        super("프로필을 수정할 권한이 없습니다.");
    }
}
