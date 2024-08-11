package com.atwoz.member.exception.exceptions.member.profile.style;

public class MemberStyleSizeException extends RuntimeException {

    public MemberStyleSizeException() {
        super("선택할 수 있는 스타일 갯수와 맞지 않습니다.");
    }
}
