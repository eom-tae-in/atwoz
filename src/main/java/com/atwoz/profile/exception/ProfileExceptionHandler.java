package com.atwoz.profile.exception;

import com.atwoz.profile.exception.exceptions.AgeRangeException;
import com.atwoz.profile.exception.exceptions.DuplicatedUserHobbyException;
import com.atwoz.profile.exception.exceptions.HeightRangeException;
import com.atwoz.profile.exception.exceptions.InvalidDrinkException;
import com.atwoz.profile.exception.exceptions.InvalidGenderException;
import com.atwoz.profile.exception.exceptions.InvalidGraduateException;
import com.atwoz.profile.exception.exceptions.InvalidJobException;
import com.atwoz.profile.exception.exceptions.InvalidMbtiException;
import com.atwoz.profile.exception.exceptions.InvalidProfileAccessStatusException;
import com.atwoz.profile.exception.exceptions.InvalidReligionException;
import com.atwoz.profile.exception.exceptions.InvalidSmokeException;
import com.atwoz.profile.exception.exceptions.LatitudeRangeException;
import com.atwoz.profile.exception.exceptions.LongitudeRangeException;
import com.atwoz.profile.exception.exceptions.ProfileNotFoundException;
import com.atwoz.profile.exception.exceptions.UnauthorizedProfileModificationException;
import com.atwoz.profile.exception.exceptions.UserHobbySizeException;
import com.atwoz.profile.exception.exceptions.UserNicknameAlreadyExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ProfileExceptionHandler {

    @ExceptionHandler(UserNicknameAlreadyExistedException.class)
    public ResponseEntity<String> handleUserNicknameAlreadyExistedException(
            final UserNicknameAlreadyExistedException e
    ) {
        return getErrorMessageWithStatus(e, HttpStatus.CONFLICT);
    }

    // user_hobbies
    @ExceptionHandler(DuplicatedUserHobbyException.class)
    public ResponseEntity<String> handleHobbyDuplicationException(final DuplicatedUserHobbyException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserHobbySizeException.class)
    public ResponseEntity<String> handleHobbySizeException(final UserHobbySizeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(InvalidGenderException.class)
    public ResponseEntity<String> handleGenderInvalidException(final InvalidGenderException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    // location
    @ExceptionHandler(LatitudeRangeException.class)
    public ResponseEntity<String> handleLatitudeRangeException(final LatitudeRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LongitudeRangeException.class)
    public ResponseEntity<String> handleLongitudeRangeException(final LongitudeRangeException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    // profile
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

    @ExceptionHandler(ProfileNotFoundException.class)
    public ResponseEntity<String> handleProfileNotFoundException(final ProfileNotFoundException e) {
        return getErrorMessageWithStatus(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedProfileModificationException.class)
    public ResponseEntity<String> handleUnauthorizedProfileModificationException(
            final UnauthorizedProfileModificationException e
    ) {
        return getErrorMessageWithStatus(e, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<String> getErrorMessageWithStatus(final Exception e, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(e.getMessage());
    }
}
