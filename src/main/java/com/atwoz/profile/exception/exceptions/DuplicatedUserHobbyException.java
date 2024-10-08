package com.atwoz.profile.exception.exceptions;

public class DuplicatedUserHobbyException extends RuntimeException {

    public DuplicatedUserHobbyException() {
        super("취미를 중복 선택하면 안 됩니다.");
    }
}
