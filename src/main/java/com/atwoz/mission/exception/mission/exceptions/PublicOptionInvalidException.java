package com.atwoz.mission.exception.mission.exceptions;

public class PublicOptionInvalidException extends RuntimeException {

    public PublicOptionInvalidException() {
        super("공개 여부는 public 혹은 private 으로 입력해주세요.");
    }
}
