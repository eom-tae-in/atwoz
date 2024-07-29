package com.atwoz.alert.exception.exceptions;

public class AlertGroupNotFoundException extends RuntimeException {

    public AlertGroupNotFoundException() {
        super("알림 전송 그룹을 찾을 수 없습니다.");
    }
}
