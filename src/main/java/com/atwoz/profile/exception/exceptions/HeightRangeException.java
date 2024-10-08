package com.atwoz.profile.exception.exceptions;

public class HeightRangeException extends RuntimeException {

    public HeightRangeException() {
        super("키 범위가 올바르지 않습니다.");
    }
}
