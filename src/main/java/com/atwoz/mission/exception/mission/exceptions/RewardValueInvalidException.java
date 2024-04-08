package com.atwoz.mission.exception.mission.exceptions;

public class RewardValueInvalidException extends RuntimeException {

    public RewardValueInvalidException() {
        super("미션의 보상은 0개 이상이어야합니다.");
    }
}
