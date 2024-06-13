package com.atwoz.report.exception.exceptions;

public class InvalidReportTypeException extends RuntimeException {

    public InvalidReportTypeException() {
        super("유효하지 않은 신고 사유입니다.");
    }
}
