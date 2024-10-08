package com.atwoz.hobby.exception.exceptions;

public class InvalidHobbyException extends RuntimeException {

    public InvalidHobbyException() {
        super("등록되지 않은 취미입니다.");
    }
}
