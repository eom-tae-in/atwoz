package com.atwoz.mission.exception.membermission.exceptions;

public class AlreadyChallengeMissionExistedException extends RuntimeException {

    public AlreadyChallengeMissionExistedException() {
        super("이미 회원의 목록 안에 있는 챌린지 미션입니다. 챌린지 미션은 한 번만 가능합니다.");
    }
}
