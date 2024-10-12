package com.atwoz.member.exception;

import com.atwoz.member.exception.exceptions.auth.ExpiredTokenException;
import com.atwoz.member.exception.exceptions.auth.InvalidJsonKeyException;
import com.atwoz.member.exception.exceptions.auth.JsonDataInvalidException;
import com.atwoz.member.exception.exceptions.auth.MemberLoginInvalidException;
import com.atwoz.member.exception.exceptions.auth.OAuthPlatformNotFountException;
import com.atwoz.member.exception.exceptions.auth.SignatureInvalidException;
import com.atwoz.member.exception.exceptions.auth.TokenFormInvalidException;
import com.atwoz.member.exception.exceptions.auth.TokenInvalidException;
import com.atwoz.member.exception.exceptions.auth.UnsupportedTokenException;
import com.atwoz.member.exception.exceptions.member.InvalidContactTypeException;
import com.atwoz.member.exception.exceptions.member.InvalidMemberAccountStatusException;
import com.atwoz.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberContactInfoAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    // member
    @ExceptionHandler(MemberAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberAlreadyExistedException(final MemberAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(final MemberNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidMemberAccountStatusException.class)
    public ResponseEntity<String> handleMemberAccountStatusInvalidException(
            final InvalidMemberAccountStatusException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberContactInfoAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberContactInfoAlreadyExistedException(
            final MemberContactInfoAlreadyExistedException e
    ) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidContactTypeException.class)
    public ResponseEntity<String> handleContactTypeInvalidException(final InvalidContactTypeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    // auth
    @ExceptionHandler(SignatureInvalidException.class)
    public ResponseEntity<String> handleSignatureInvalidException(final SignatureInvalidException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenFormInvalidException.class)
    public ResponseEntity<String> handleTokenFormInvalidException(final TokenFormInvalidException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> handleExpiredTokenException(final ExpiredTokenException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<String> handleUnsupportedTokenException(final UnsupportedTokenException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<String> handleTokenInvalidException(final TokenInvalidException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MemberLoginInvalidException.class)
    public ResponseEntity<String> handleLoginInvalidException(final MemberLoginInvalidException e) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JsonDataInvalidException.class)
    public ResponseEntity<String> handleJsonDataInvalidException(final JsonDataInvalidException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OAuthPlatformNotFountException.class)
    public ResponseEntity<String> handleOAuthPlatformNotFountException(final OAuthPlatformNotFountException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidJsonKeyException.class)
    public ResponseEntity<String> handleJsonKeyInvalidException(final InvalidJsonKeyException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getErrorMessageWithStatus(final Exception e, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(e.getMessage());
    }
}
