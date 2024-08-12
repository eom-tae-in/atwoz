package com.atwoz.member.exception.exceptions.member.profile.hobby;

public class MemberHobbySizeException extends RuntimeException {

    public MemberHobbySizeException() {
        super("선택할 수 있는 취미 갯수와 맞지 않습니다.");
    }
}
