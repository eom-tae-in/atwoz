package com.atwoz.report.exception.exceptions;

public class ReportNotFoundException extends RuntimeException {

    public ReportNotFoundException() {
        super("신고 정보를 찾을 수 없습니다.");
    }
}
