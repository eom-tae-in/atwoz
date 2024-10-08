package com.atwoz.member.exception.exceptions.member;

public class InvalidContactTypeException extends RuntimeException {

    public InvalidContactTypeException() {
        super("유효하지 않은 연락처 타입입니다.");
    }
}
