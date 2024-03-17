package com.atwoz.mission.exception.membermission;

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

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
