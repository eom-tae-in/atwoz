package com.atwoz.mission.exception.mission.exceptions;

public class MissionNotFoundException extends RuntimeException {

    public MissionNotFoundException() {
        super("미션을 찾을 수 없습니다.");
    }
}
