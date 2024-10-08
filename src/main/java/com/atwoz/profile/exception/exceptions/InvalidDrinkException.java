package com.atwoz.profile.exception.exceptions;

public class InvalidDrinkException extends RuntimeException {

    public InvalidDrinkException() {
        super("등록되지 않은 음주 단계입니다.");
    }
}
