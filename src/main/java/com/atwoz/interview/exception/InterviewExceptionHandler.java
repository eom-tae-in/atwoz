package com.atwoz.interview.exception;

import com.atwoz.interview.exception.exceptions.AlreadyExistedInterviewQuestionException;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InterviewExceptionHandler {

    @ExceptionHandler(AlreadyExistedInterviewQuestionException.class)
    public ResponseEntity<String> handleAlreadyExistedInterviewQuestionException(final AlreadyExistedInterviewQuestionException e) {
        return getExceptionWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InterviewTypeNotFoundException.class)
    public ResponseEntity<String> handleInterviewTypeNotFoundException(final InterviewTypeNotFoundException e) {
        return getExceptionWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InterviewNotFoundException.class)
    public ResponseEntity<String> handleInterviewNotFoundException(final InterviewNotFoundException e) {
        return getExceptionWithStatus(e, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
