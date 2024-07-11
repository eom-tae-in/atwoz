package com.atwoz.admin.exception.exceptions;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("리프레쉬 토큰이 유효하지 않습니다");
    }
}
