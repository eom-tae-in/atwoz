package com.atwoz.admin.exception;

import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import com.atwoz.admin.exception.exceptions.AdminNotFoundException;
import com.atwoz.admin.exception.exceptions.InvalidPasswordException;
import com.atwoz.admin.exception.exceptions.PasswordMismatchException;
import com.atwoz.admin.exception.exceptions.UnauthorizedAccessToAdminException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdminExceptionHandler {

    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<String> handlePasswordMismatchException(final PasswordMismatchException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(AdminNotFoundException.class)
    public ResponseEntity<String> handleAdminNotFoundException(final AdminNotFoundException e) {
        return getNotFoundResponse(e);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> handlePasswordInvalidException(final InvalidPasswordException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(UnauthorizedAccessToAdminException.class)
    public ResponseEntity<String> handleUnauthorizedAccessToAdminException(final UnauthorizedAccessToAdminException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(AdminLoginInvalidException.class)
    public ResponseEntity<String> handleAdminLoginInvalidException(final AdminLoginInvalidException e) {
        return getUnauthorized(e);
    }

    private ResponseEntity<String> getNotFoundResponse(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    private ResponseEntity<String> getUnauthorized(final Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    private ResponseEntity<String> getConflicted(final Exception e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }

    private ResponseEntity<String> getBadRequest(final Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}

