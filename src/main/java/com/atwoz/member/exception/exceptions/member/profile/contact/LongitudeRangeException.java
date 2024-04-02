package com.atwoz.member.exception.exceptions.member.profile.contact;

public class LongitudeRangeException extends RuntimeException {

    public LongitudeRangeException() {
        super("경도 범위가 올바르지 않습니다.");
    }
}
