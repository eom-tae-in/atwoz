package com.atwoz.member.exception.exceptions.info.option;

public class InvalidReligionException extends RuntimeException {

    public InvalidReligionException() {
        super("등록되지 않은 종교입니다.");
    }
}
