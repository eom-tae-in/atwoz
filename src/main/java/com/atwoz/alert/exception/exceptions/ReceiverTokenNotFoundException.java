package com.atwoz.alert.exception.exceptions;

public class ReceiverTokenNotFoundException extends RuntimeException {

    public ReceiverTokenNotFoundException() {
        super("대상 회원의 FCM 토큰이 존재하지 않습니다.");
    }
}
