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
import com.atwoz.member.exception.exceptions.member.MemberAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNicknameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.exception.exceptions.member.RoleNotFoundException;
import com.atwoz.member.exception.exceptions.member.profile.HobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.HobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidDrinkException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGenderException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGraduateException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidJobException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidMbtiException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidProfileAccessStatusException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidReligionException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidSmokeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.LatitudeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.LongitudeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.StyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.StyleSizeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.AgeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.HeightRangeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(final RoleNotFoundException e) {
        return getNotFoundResponse(e);
    }

    @ExceptionHandler(MemberNicknameAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberNicknameAlreadyExistedException(
            final MemberNicknameAlreadyExistedException e) {
        return getConflicted(e);
    }

    @ExceptionHandler(MemberAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberAlreadyExistedException(final MemberAlreadyExistedException e) {
        return getConflicted(e);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(final MemberNotFoundException e) {
        return getNotFoundResponse(e);
    }

    @ExceptionHandler(SignatureInvalidException.class)
    public ResponseEntity<String> handleSignatureInvalidException(final SignatureInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(TokenFormInvalidException.class)
    public ResponseEntity<String> handleTokenFormInvalidException(final TokenFormInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<String> handleExpiredTokenException(final ExpiredTokenException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(UnsupportedTokenException.class)
    public ResponseEntity<String> handleUnsupportedTokenException(final UnsupportedTokenException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(TokenInvalidException.class)
    public ResponseEntity<String> handleTokenInvalidException(final TokenInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(MemberLoginInvalidException.class)
    public ResponseEntity<String> handleLoginInvalidException(final MemberLoginInvalidException e) {
        return getUnauthorized(e);
    }

    @ExceptionHandler(JsonDataInvalidException.class)
    public ResponseEntity<String> handleJsonDataInvalidException(final JsonDataInvalidException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(OAuthPlatformNotFountException.class)
    public ResponseEntity<String> handleOAuthPlatformNotFountException(final OAuthPlatformNotFountException e) {
        return getNotFoundResponse(e);
    }

    @ExceptionHandler(InvalidHobbyException.class)
    public ResponseEntity<String> handleHobbyInvalidException(final InvalidHobbyException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(HobbySizeException.class)
    public ResponseEntity<String> handleHobbySizeException(final HobbySizeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(HobbyDuplicateException.class)
    public ResponseEntity<String> handleHobbyDuplicationException(final HobbyDuplicateException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidStyleException.class)
    public ResponseEntity<String> handleStyleInvalidException(final InvalidStyleException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(StyleSizeException.class)
    public ResponseEntity<String> handleStyleSizeException(final StyleSizeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(StyleDuplicateException.class)
    public ResponseEntity<String> handleStyleDuplicateException(final StyleDuplicateException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(AgeRangeException.class)
    public ResponseEntity<String> handleAgeRangeException(final AgeRangeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(HeightRangeException.class)
    public ResponseEntity<String> handleHeightRangeException(final HeightRangeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<String> handleGenderInvalidException(final InvalidGenderException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(LatitudeRangeException.class)
    public ResponseEntity<String> handleLatitudeRangeException(final LatitudeRangeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(LongitudeRangeException.class)
    public ResponseEntity<String> handleLongitudeRangeException(final LongitudeRangeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidJobException.class)
    public ResponseEntity<String> handleJobInvalidException(final InvalidJobException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidDrinkException.class)
    public ResponseEntity<String> handleDrinkNInvalidException(final InvalidDrinkException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidGraduateException.class)
    public ResponseEntity<String> handleGraduateInvalidException(final InvalidGraduateException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidMbtiException.class)
    public ResponseEntity<String> handleMbtiInvalidException(final InvalidMbtiException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidSmokeException.class)
    public ResponseEntity<String> handleSmokeInvalidException(final InvalidSmokeException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidReligionException.class)
    public ResponseEntity<String> handleReligionInvalidException(final InvalidReligionException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidProfileAccessStatusException.class)
    public ResponseEntity<String> handleProfileInvalidException(final InvalidProfileAccessStatusException e) {
        return getBadRequest(e);
    }

    @ExceptionHandler(InvalidJsonKeyException.class)
    public ResponseEntity<String> handleJsonKeyInvalidException(final InvalidJsonKeyException e) {
        return getBadRequest(e);
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
