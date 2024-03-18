package com.atwoz.mission.exception.membermission.exceptions;

public class MemberMissionNotClearException extends RuntimeException {

    public MemberMissionNotClearException() {
        super("미션을 클리어하지 않아 보상을 얻을 수 없습니다.");
    }
}
