package com.atwoz.like.exception;

import com.atwoz.like.exception.exceptions.LikeTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LikeExceptionHandler {

    @ExceptionHandler(LikeTypeNotFoundException.class)
    public ResponseEntity<String> handleLikeTypeNotFoundException(final LikeTypeNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
