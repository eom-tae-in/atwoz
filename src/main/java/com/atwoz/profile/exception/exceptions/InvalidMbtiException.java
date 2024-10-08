package com.atwoz.profile.exception.exceptions;

public class InvalidMbtiException extends RuntimeException {

    public InvalidMbtiException() {
        super("등록되지 않은 MBTI입니다.");
    }
}
