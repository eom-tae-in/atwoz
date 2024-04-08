package com.atwoz.mission.exception.mission.exceptions;

public class MissionTypeInvalidException extends RuntimeException {

    public MissionTypeInvalidException() {
        super("미션 타입은 daily 혹은 challenge 로 입력해주세요.");
    }
}
