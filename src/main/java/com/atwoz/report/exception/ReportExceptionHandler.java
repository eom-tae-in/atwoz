package com.atwoz.report.exception;

import com.atwoz.report.exception.exceptions.InvalidReportResultException;
import com.atwoz.report.exception.exceptions.InvalidReportTypeException;
import com.atwoz.report.exception.exceptions.InvalidReporterException;
import com.atwoz.report.exception.exceptions.ReportNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReportExceptionHandler {

    @ExceptionHandler(ReportNotFoundException.class)
    public ResponseEntity<String> handleReportNotFoundException(final ReportNotFoundException e) {
        return getNotFoundResponse(e);
    }

    @ExceptionHandler(InvalidReportTypeException.class)
    public ResponseEntity<String> handleReportTypeInvalidException(final InvalidReportTypeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidReportResultException.class)
    public ResponseEntity<String> handleReportResultInvalidException(final InvalidReportResultException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidReporterException.class)
    public ResponseEntity<String> handleReporterInvalidException(final InvalidReporterException e) {
        return getBadRequest(e);
    }

    private ResponseEntity<String> getNotFoundResponse(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    private ResponseEntity<String> getBadRequest(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
