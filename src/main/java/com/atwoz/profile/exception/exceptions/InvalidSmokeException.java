package com.atwoz.profile.exception.exceptions;

public class InvalidSmokeException extends RuntimeException {

    public InvalidSmokeException() {
        super("등록되지 않은 흡연 단계입니다.");
    }
}
