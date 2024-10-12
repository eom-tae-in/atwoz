package com.atwoz.hobby.exception;

import com.atwoz.hobby.exception.exceptions.HobbyCodeAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNameAlreadyExistedException;
import com.atwoz.hobby.exception.exceptions.HobbyNotFoundException;
import com.atwoz.hobby.exception.exceptions.InvalidHobbyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HobbyExceptionHandler {

    @ExceptionHandler(InvalidHobbyException.class)
    public ResponseEntity<String> handleHobbyInvalidException(final InvalidHobbyException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HobbyNotFoundException.class)
    public ResponseEntity<String> handleHobbyNotFoundException(final HobbyNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HobbyNameAlreadyExistedException.class)
    public ResponseEntity<String> handleHobbyNameAlreadyExistedException(final HobbyNameAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HobbyCodeAlreadyExistedException.class)
    public ResponseEntity<String> handleHobbyCodeAlreadyExistedException(final HobbyCodeAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<String> getErrorMessageWithStatus(final Exception e, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(e.getMessage());
    }
}
