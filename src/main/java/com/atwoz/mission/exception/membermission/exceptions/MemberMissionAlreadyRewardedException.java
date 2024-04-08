package com.atwoz.mission.exception.membermission.exceptions;

public class MemberMissionAlreadyRewardedException extends RuntimeException {

    public MemberMissionAlreadyRewardedException() {
        super("이미 미션에 대한 보상을 수령하였습니다.");
    }
}
