package com.atwoz.member.exception.exceptions.info.style;

public class InvalidStyleException extends RuntimeException {

    public InvalidStyleException() {
        super("등록되지 않은 스타일입니다.");
    }
}
