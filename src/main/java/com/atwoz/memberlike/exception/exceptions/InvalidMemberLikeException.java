package com.atwoz.memberlike.exception.exceptions;

public class InvalidMemberLikeException extends RuntimeException {

    public InvalidMemberLikeException() {
        super("자기 자신에게 호감을 보낼 수 없습니다.");
    }
}
