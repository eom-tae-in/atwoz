package com.atwoz.like.exception.exceptions;

public class LikeTypeNotFoundException extends RuntimeException {

    public LikeTypeNotFoundException() {
        super("등록되지 않은 좋아요 레벨입니다.");
    }
}
