package com.atwoz.selfintro.exception.exceptions;

public class InvalidContentException extends RuntimeException {

    public InvalidContentException() {
        super("소개글 입력이 올바르지 않습니다.");
    }
}
