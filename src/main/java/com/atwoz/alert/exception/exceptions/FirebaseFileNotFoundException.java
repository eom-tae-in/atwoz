package com.atwoz.alert.exception.exceptions;

public class FirebaseFileNotFoundException extends RuntimeException {

    public FirebaseFileNotFoundException() {
        super("Firebase json 파일을 찾을 수 없습니다.");
    }
}
