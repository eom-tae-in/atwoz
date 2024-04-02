package com.atwoz.member.exception.exceptions.member.profile.physical_profile;

public class AgeRangeException extends RuntimeException {

    public AgeRangeException() {
        super("나이 범위가 올바르지 않습니다.");
    }
}
