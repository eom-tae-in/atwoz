package com.atwoz.mission.exception.membermission;

import com.atwoz.mission.exception.membermission.exceptions.AlreadyChallengeMissionExistedException;
import com.atwoz.mission.exception.membermission.exceptions.AlreadyDailyMissionExistedLimitException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionAlreadyRewardedException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionNotFoundException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionsNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberMissionsExceptionHandler {

    @ExceptionHandler(MemberMissionsNotFoundException.class)
    public ResponseEntity<String> handleMemberMissionNotFoundException(final MemberMissionsNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberMissionNotFoundException.class)
    public ResponseEntity<String> handleMemberMissionNotFoundException(final MemberMissionNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberMissionAlreadyRewardedException.class)
    public ResponseEntity<String> handleMemberMissionAlreadyRewardedException(final MemberMissionAlreadyRewardedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyChallengeMissionExistedException.class)
    public ResponseEntity<String> handleAlreadyChallengeMissionExistedException(final AlreadyChallengeMissionExistedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyDailyMissionExistedLimitException.class)
    public ResponseEntity<String> handleAlreadyDailyMissionExistedLimitException(final AlreadyDailyMissionExistedLimitException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
