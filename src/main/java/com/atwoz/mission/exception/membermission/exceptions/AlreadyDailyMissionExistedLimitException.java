package com.atwoz.mission.exception.membermission.exceptions;

public class AlreadyDailyMissionExistedLimitException extends RuntimeException {

    public AlreadyDailyMissionExistedLimitException() {
        super("오늘 진행할 수 있는 데일리 미션을 초과했습니다.");
    }
}
