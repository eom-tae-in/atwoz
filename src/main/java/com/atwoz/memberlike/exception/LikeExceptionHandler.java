package com.atwoz.memberlike.exception;

import com.atwoz.memberlike.exception.exceptions.AlreadyLikedException;
import com.atwoz.memberlike.exception.exceptions.LikeIconNotFoundException;
import com.atwoz.memberlike.exception.exceptions.LikeTypeNotFoundException;
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

    @ExceptionHandler(LikeIconNotFoundException.class)
    public ResponseEntity<String> handleLikeIconNotFoundException(final LikeIconNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyLikedException.class)
    public ResponseEntity<String> handleAlreadyLikedException(final AlreadyLikedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
