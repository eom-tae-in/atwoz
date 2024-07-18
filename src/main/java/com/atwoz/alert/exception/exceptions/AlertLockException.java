package com.atwoz.alert.exception.exceptions;

public class AlertLockException extends RuntimeException {

    public AlertLockException() {
        super("알림 락 획득 과정에서 예외가 발생하였습니다.");
    }
}
