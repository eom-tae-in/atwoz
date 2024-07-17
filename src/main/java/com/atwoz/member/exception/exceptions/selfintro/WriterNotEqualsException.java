package com.atwoz.member.exception.exceptions.selfintro;

public class WriterNotEqualsException extends RuntimeException {

    public WriterNotEqualsException() {
        super("글쓴이가 일치하지 않습니다");
    }
}
