package com.atwoz.profile.exception.exceptions;

public class InvalidProfileAccessStatusException extends RuntimeException {

    public InvalidProfileAccessStatusException() {
        super("올바르지 않은 프로필 접근 상태 정보입니다.");
    }
}
