package com.atwoz.job.exception.exceptions;

public class JobCodeAlreadyExistedException extends RuntimeException {

    public JobCodeAlreadyExistedException() {
        super("이미 존재하는 스타일 코드입니다.");
    }
}
