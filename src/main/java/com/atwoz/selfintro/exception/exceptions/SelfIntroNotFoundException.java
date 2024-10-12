package com.atwoz.selfintro.exception.exceptions;

public class SelfIntroNotFoundException extends RuntimeException {

    public SelfIntroNotFoundException() {
        super("셀프 소개글을 찾을 수 없습니다.");
    }
}
