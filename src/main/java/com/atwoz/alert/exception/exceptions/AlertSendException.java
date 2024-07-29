package com.atwoz.alert.exception.exceptions;

public class AlertSendException extends RuntimeException {

    public AlertSendException() {
        super("알림 전송 과정에서 예외가 발생했습니다.");
    }
}
