package com.atwoz.report.exception.exceptions;

public class InvalidReporterException extends RuntimeException {

    public InvalidReporterException() {
        super("유효하지 않은 신고자 정보입니다");
    }
}
