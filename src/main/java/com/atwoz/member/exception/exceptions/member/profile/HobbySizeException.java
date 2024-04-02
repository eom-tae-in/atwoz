package com.atwoz.member.exception.exceptions.member.profile;

public class HobbySizeException extends RuntimeException {

    public HobbySizeException() {
        super("선택할 수 있는 취미 갯수와 맞지 않습니다.");
    }
}
