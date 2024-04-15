package com.atwoz.member.exception.exceptions.member.profile;

public class InvalidStyleException extends RuntimeException {

    public InvalidStyleException() {
        super("등록되지 않은 스타일입니다.");
    }
}
