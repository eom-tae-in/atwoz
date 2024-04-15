package com.atwoz.member.exception.exceptions.member.profile;

public class StyleSizeException extends RuntimeException {

    public StyleSizeException() {
        super("선택할 수 있는 스타일 갯수와 맞지 않습니다.");
    }
}
