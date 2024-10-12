package com.atwoz.profile.exception.exceptions;

public class InvalidGenderException extends RuntimeException {

    public InvalidGenderException() {
        super("등록되지 않은 성별입니다.");
    }
}
