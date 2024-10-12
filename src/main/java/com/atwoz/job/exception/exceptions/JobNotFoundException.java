package com.atwoz.job.exception.exceptions;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() {
        super("직업을 찾을 수 없습니다.");
    }
}
