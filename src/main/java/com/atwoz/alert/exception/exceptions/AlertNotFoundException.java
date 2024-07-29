package com.atwoz.alert.exception.exceptions;

public class AlertNotFoundException extends RuntimeException {

    public AlertNotFoundException() {
        super("회원과 ID에 해당되는 알림 내역이 없습니다.");
    }
}
