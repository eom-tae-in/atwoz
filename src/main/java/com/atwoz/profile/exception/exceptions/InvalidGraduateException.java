package com.atwoz.profile.exception.exceptions;

public class InvalidGraduateException extends RuntimeException {

    public InvalidGraduateException() {
        super("등록되지 않은 최종 학력입니다.");
    }
}
