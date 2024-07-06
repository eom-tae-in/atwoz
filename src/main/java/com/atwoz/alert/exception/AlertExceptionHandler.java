package com.atwoz.alert.exception;

import com.atwoz.alert.exception.exceptions.AlertSendException;
import com.atwoz.alert.exception.exceptions.FirebaseFileNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlertExceptionHandler {

    @ExceptionHandler(FirebaseFileNotFoundException.class)
    public ResponseEntity<String> handleFirebaseFileNotFoundException(final FirebaseFileNotFoundException e) {
        return getExceptionWithStatus(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AlertSendException.class)
    public ResponseEntity<String> handleAlertSendException(final AlertSendException e) {
        return getExceptionWithStatus(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
