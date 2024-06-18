package com.atwoz.memberlike.exception.exceptions;

public class AlreadyLikedException extends RuntimeException {

    public AlreadyLikedException() {
        super("이미 호감을 보낸 상대입니다. 만료일 후 다시 보낼 수 있습니다.");
    }
}
