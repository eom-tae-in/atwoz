package com.atwoz.profile.exception.exceptions;

public class UserNicknameAlreadyExistedException extends RuntimeException {

    public UserNicknameAlreadyExistedException() {
        super("이미 존재하는 닉네임입니다.");
    }
}
