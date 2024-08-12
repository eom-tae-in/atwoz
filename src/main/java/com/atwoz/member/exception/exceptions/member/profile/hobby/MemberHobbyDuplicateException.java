package com.atwoz.member.exception.exceptions.member.profile.hobby;

public class MemberHobbyDuplicateException extends RuntimeException {

    public MemberHobbyDuplicateException() {
        super("취미를 중복 선택하면 안 됩니다.");
    }
}
