package com.atwoz.mission.exception.mission;

import com.atwoz.mission.exception.mission.exceptions.MissionNotClearException;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.exception.mission.exceptions.MissionTypeInvalidException;
import com.atwoz.mission.exception.mission.exceptions.PublicOptionInvalidException;
import com.atwoz.mission.exception.mission.exceptions.RewardValueInvalidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MissionExceptionHandler {

    @ExceptionHandler(MissionNotClearException.class)
    public ResponseEntity<String> handleMissionNotClearException(final MissionNotClearException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissionNotFoundException.class)
    public ResponseEntity<String> handleMissionNotFoundException(final MissionNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MissionTypeInvalidException.class)
    public ResponseEntity<String> handleMissionTypeInvalidException(final MissionTypeInvalidException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PublicOptionInvalidException.class)
    public ResponseEntity<String> handlePublicOptionInvalidException(final PublicOptionInvalidException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RewardValueInvalidException.class)
    public ResponseEntity<String> handleRewardValueInvalidException(final RewardValueInvalidException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
