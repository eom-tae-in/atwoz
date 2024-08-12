package com.atwoz.member.exception.exceptions.member.profile.style;

public class StyleCodeAlreadyExistedException extends RuntimeException {

    public StyleCodeAlreadyExistedException() {
        super("이미 존재하는 스타일 코드입니다.");
    }
}
