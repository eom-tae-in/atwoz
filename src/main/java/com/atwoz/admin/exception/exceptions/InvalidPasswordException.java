package com.atwoz.admin.exception.exceptions;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("잘못된 이메일/비밀번호 입력입니다. 다시 입력해주세요");
    }
}
