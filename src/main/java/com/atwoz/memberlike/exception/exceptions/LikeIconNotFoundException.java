package com.atwoz.memberlike.exception.exceptions;

public class LikeIconNotFoundException extends RuntimeException {

    public LikeIconNotFoundException() {
        super("등록되지 않은 좋아요 아이콘입니다.");
    }
}
