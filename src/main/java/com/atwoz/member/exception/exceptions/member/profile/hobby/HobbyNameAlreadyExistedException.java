package com.atwoz.member.exception.exceptions.member.profile.hobby;

public class HobbyNameAlreadyExistedException extends RuntimeException {

    public HobbyNameAlreadyExistedException() {
        super("이미 존재하는 취미 이름입니다.");
    }
}
