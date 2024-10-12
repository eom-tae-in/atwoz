package com.atwoz.profile.exception.exceptions;

public class ProfileNotFoundException extends RuntimeException {

    public ProfileNotFoundException() {
        super("프로필을 찾을 수 없습니다.");
    }
}
