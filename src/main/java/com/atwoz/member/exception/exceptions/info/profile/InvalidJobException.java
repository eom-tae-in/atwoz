package com.atwoz.member.exception.exceptions.info.profile;

public class InvalidJobException extends RuntimeException {

    public InvalidJobException() {
        super("직업을 찾을 수 없습니다.");
    }
}
