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
import com.atwoz.member.exception.exceptions.member.profile.InvalidDrinkException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGenderException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGraduateException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidJobException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidMbtiException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidProfileAccessStatusException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidReligionException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidSmokeException;
import com.atwoz.member.exception.exceptions.member.profile.LatitudeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.LongitudeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyCodeAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.HobbyNotFoundException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.InvalidHobbyException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.AgeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.HeightRangeException;
import com.atwoz.member.exception.exceptions.member.profile.style.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleSizeException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleCodeAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNameAlreadyExistedException;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import com.atwoz.member.exception.exceptions.selfintro.InvalidContentException;
import com.atwoz.member.exception.exceptions.selfintro.SelfIntroNotFoundException;
import com.atwoz.member.exception.exceptions.selfintro.WriterNotEqualsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    // member
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> handleRoleNotFoundException(final RoleNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MemberNicknameAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberNicknameAlreadyExistedException(
            final MemberNicknameAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberAlreadyExistedException.class)
    public ResponseEntity<String> handleMemberAlreadyExistedException(final MemberAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> handleMemberNotFoundException(final MemberNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
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

    // hobby
    @ExceptionHandler(InvalidHobbyException.class)
    public ResponseEntity<String> handleHobbyInvalidException(final InvalidHobbyException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberHobbySizeException.class)
    public ResponseEntity<String> handleHobbySizeException(final MemberHobbySizeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberHobbyDuplicateException.class)
    public ResponseEntity<String> handleHobbyDuplicationException(final MemberHobbyDuplicateException e) {
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

    // style
    @ExceptionHandler(InvalidStyleException.class)
    public ResponseEntity<String> handleStyleInvalidException(final InvalidStyleException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberStyleSizeException.class)
    public ResponseEntity<String> handleStyleSizeException(final MemberStyleSizeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MemberStyleDuplicateException.class)
    public ResponseEntity<String> handleStyleDuplicateException(final MemberStyleDuplicateException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StyleNotFoundException.class)
    public ResponseEntity<String> handleStyleNotFoundException(final StyleNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StyleNameAlreadyExistedException.class)
    public ResponseEntity<String> handleStyleNameAlreadyExistedException(final StyleNameAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StyleCodeAlreadyExistedException.class)
    public ResponseEntity<String> handleStyleCodeAlreadyExistedException(final StyleCodeAlreadyExistedException e) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    // physical_profile
    @ExceptionHandler(AgeRangeException.class)
    public ResponseEntity<String> handleAgeRangeException(final AgeRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HeightRangeException.class)
    public ResponseEntity<String> handleHeightRangeException(final HeightRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    // profile
    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<String> handleGenderInvalidException(final InvalidGenderException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LatitudeRangeException.class)
    public ResponseEntity<String> handleLatitudeRangeException(final LatitudeRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LongitudeRangeException.class)
    public ResponseEntity<String> handleLongitudeRangeException(final LongitudeRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJobException.class)
    public ResponseEntity<String> handleJobInvalidException(final InvalidJobException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDrinkException.class)
    public ResponseEntity<String> handleDrinkNInvalidException(final InvalidDrinkException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidGraduateException.class)
    public ResponseEntity<String> handleGraduateInvalidException(final InvalidGraduateException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidMbtiException.class)
    public ResponseEntity<String> handleMbtiInvalidException(final InvalidMbtiException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSmokeException.class)
    public ResponseEntity<String> handleSmokeInvalidException(final InvalidSmokeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidReligionException.class)
    public ResponseEntity<String> handleReligionInvalidException(final InvalidReligionException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProfileAccessStatusException.class)
    public ResponseEntity<String> handleProfileInvalidException(final InvalidProfileAccessStatusException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    // selfInfo
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
