package com.atwoz.member.exception.exceptions.member.profile.style;

public class StyleNameAlreadyExistedException extends RuntimeException {

    public StyleNameAlreadyExistedException() {
        super("이미 존재하는 스타일 이름입니다");
    }
}
