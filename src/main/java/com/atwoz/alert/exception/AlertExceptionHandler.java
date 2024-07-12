package com.atwoz.alert.exception;

import com.atwoz.alert.exception.exceptions.AlertNotFoundException;
import com.atwoz.alert.exception.exceptions.AlertSendException;
import com.atwoz.alert.exception.exceptions.ReceiverTokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AlertExceptionHandler {

    @ExceptionHandler(AlertSendException.class)
    public ResponseEntity<String> handleAlertSendException(final AlertSendException e) {
        return getExceptionWithStatus(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReceiverTokenNotFoundException.class)
    public ResponseEntity<String> handleReceiverTokenNotFoundException(final ReceiverTokenNotFoundException e) {
        return getExceptionWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlertNotFoundException.class)
    public ResponseEntity<String> handleAlertNotFoundException(final AlertNotFoundException e) {
        return getExceptionWithStatus(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
