package com.atwoz.admin.exception.exceptions;

public class UnauthorizedAccessToAdminException extends RuntimeException {

    public UnauthorizedAccessToAdminException() {
        super("권한이 없습니다.");
    }
}
