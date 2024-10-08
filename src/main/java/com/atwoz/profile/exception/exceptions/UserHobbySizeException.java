package com.atwoz.profile.exception.exceptions;

public class UserHobbySizeException extends RuntimeException {

    public UserHobbySizeException() {
        super("선택할 수 있는 취미 갯수와 맞지 않습니다.");
    }
}
