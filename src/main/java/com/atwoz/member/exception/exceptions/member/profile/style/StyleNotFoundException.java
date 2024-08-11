package com.atwoz.member.exception.exceptions.member.profile.style;

public class StyleNotFoundException extends RuntimeException {

    public StyleNotFoundException() {
        super("스타일을 찾을 수 없습니다.");
    }
}
