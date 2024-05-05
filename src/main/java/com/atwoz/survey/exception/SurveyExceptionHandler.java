package com.atwoz.survey.exception;

import com.atwoz.survey.exception.exceptions.SurveyNameAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SurveyExceptionHandler {

    @ExceptionHandler(SurveyNameAlreadyExistException.class)
    public ResponseEntity<?> handleSurveyNameAlreadyExistException(final SurveyNameAlreadyExistException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
