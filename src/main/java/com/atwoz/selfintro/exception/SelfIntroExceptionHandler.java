package com.atwoz.selfintro.exception;

import com.atwoz.selfintro.exception.exceptions.InvalidContentException;
import com.atwoz.selfintro.exception.exceptions.SelfIntroNotFoundException;
import com.atwoz.selfintro.exception.exceptions.WriterNotEqualsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SelfIntroExceptionHandler {

    @ExceptionHandler(InvalidContentException.class)
    public ResponseEntity<String> handleContentInvalidException(final InvalidContentException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SelfIntroNotFoundException.class)
    public ResponseEntity<String> handleSelfIntroNotFoundException(final SelfIntroNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WriterNotEqualsException.class)
    public ResponseEntity<String> handleWriterNotEqualsException(final WriterNotEqualsException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> getErrorMessageWithStatus(final Exception e, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(e.getMessage());
    }
}
