package com.atwoz.member.exception.exceptions.member.profile.style;

public class MemberStyleDuplicateException extends RuntimeException {

    public MemberStyleDuplicateException() {
        super("스타일을 중복 선택하면 안 됩니다.");
    }
}
