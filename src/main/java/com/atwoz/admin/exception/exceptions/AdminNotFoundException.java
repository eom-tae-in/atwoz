package com.atwoz.admin.exception.exceptions;

public class AdminNotFoundException extends RuntimeException {

    public AdminNotFoundException() {
        super("관리자를 찾을 수 없습니다.");
    }
}
