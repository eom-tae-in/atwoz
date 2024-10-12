package com.atwoz.profile.exception.exceptions;

public class InvalidJobException extends RuntimeException {

    public InvalidJobException() {
        super("직업을 찾을 수 없습니다.");
    }
}
