package com.atwoz.job.exception.exceptions;

public class JobNameAlreadyExistedException extends RuntimeException {

    public JobNameAlreadyExistedException() {
        super("이미 존재하는 스타일 이름입니다");
    }
}
