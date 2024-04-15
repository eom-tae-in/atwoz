package com.atwoz.member.exception.exceptions.auth;

public class InvalidJsonKeyException extends RuntimeException {

    public InvalidJsonKeyException() {
        super("JSON 키 값이 유효하지 않습니다.");
    }
}
