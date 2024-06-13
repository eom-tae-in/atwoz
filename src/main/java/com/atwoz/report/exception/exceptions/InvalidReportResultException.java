package com.atwoz.report.exception.exceptions;

public class InvalidReportResultException extends RuntimeException {

    public InvalidReportResultException() {
        super("유효하지 않은 신고 결과입니다.");
    }
}
