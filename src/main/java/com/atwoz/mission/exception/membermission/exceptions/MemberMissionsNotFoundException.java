package com.atwoz.mission.exception.membermission.exceptions;

public class MemberMissionsNotFoundException extends RuntimeException {

    public MemberMissionsNotFoundException() {
        super("회원의 미션이 아예 없습니다.");
    }
}
