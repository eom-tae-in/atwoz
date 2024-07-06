package com.atwoz.admin.exception.exceptions;

public class AdminLoginInvalidException extends RuntimeException {

    public AdminLoginInvalidException() {
        super("관리자 로그인이 유효하지 않습니다");
    }
}
