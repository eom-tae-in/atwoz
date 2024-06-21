package com.atwoz.memberlike.exception.exceptions;

public class LikeLevelNotFoundException extends RuntimeException {

    public LikeLevelNotFoundException() {
        super("등록되지 않은 좋아요 레벨입니다.");
    }
}
