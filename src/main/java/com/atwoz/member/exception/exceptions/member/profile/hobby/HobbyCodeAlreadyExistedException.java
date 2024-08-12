package com.atwoz.member.exception.exceptions.member.profile.hobby;

public class HobbyCodeAlreadyExistedException extends RuntimeException {

    public HobbyCodeAlreadyExistedException() {
        super("이미 존재하는 취미 코드 입니다.");
    }
}
