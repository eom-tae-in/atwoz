package com.atwoz.job.exception.exceptions;

public class InvalidJobException extends RuntimeException {

    public InvalidJobException() {
        super("등록되지 않은 직업입니다.");
    }
}
