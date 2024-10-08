package com.atwoz.hobby.exception.exceptions;

public class HobbyNotFoundException extends RuntimeException {

    public HobbyNotFoundException() {
        super("취미를 찾을 수 없습니다.");
    }
}
