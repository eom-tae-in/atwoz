package com.atwoz.mission.exception.membermission.exceptions;

public class MemberMissionNotFoundException extends RuntimeException {

    public MemberMissionNotFoundException() {
        super("미션이 회원 미션 목록에 존재하지 않습니다.");
    }
}
