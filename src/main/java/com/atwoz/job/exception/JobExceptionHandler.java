package com.atwoz.job.exception;

import com.atwoz.job.exception.exceptions.InvalidJobException;
import com.atwoz.job.exception.exceptions.JobCodeAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNameAlreadyExistedException;
import com.atwoz.job.exception.exceptions.JobNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class JobExceptionHandler {

    @ExceptionHandler(InvalidJobException.class)
    public ResponseEntity<String> handleJobInvalidException(final InvalidJobException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<String> handleJobNotFoundException(final JobNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JobNameAlreadyExistedException.class)
    public ResponseEntity<String> handleJobNameAlreadyExistedException(final JobNameAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(JobCodeAlreadyExistedException.class)
    public ResponseEntity<String> handleJobCodeAlreadyExistedException(final JobCodeAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    private ResponseEntity<String> getErrorMessageWithStatus(final Exception e, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(e.getMessage());
    }
}
