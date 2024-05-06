package com.atwoz.survey.exception.survey;

import com.atwoz.survey.exception.survey.exceptions.SurveyAnswerDuplicatedException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNotFoundException;
import com.atwoz.survey.exception.survey.exceptions.SurveyQuestionDuplicatedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SurveyExceptionHandler {

    @ExceptionHandler(SurveyNameAlreadyExistException.class)
    public ResponseEntity<String> handleSurveyNameAlreadyExistException(final SurveyNameAlreadyExistException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveyAnswerDuplicatedException.class)
    public ResponseEntity<String> handleSurveyAnswerDuplicatedException(final SurveyAnswerDuplicatedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveyNotFoundException.class)
    public ResponseEntity<String> handleSurveyNotFoundException(final SurveyNotFoundException exception) {
        return getExceptionWithStatus(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SurveyQuestionDuplicatedException.class)
    public ResponseEntity<String> handleSurveyQuestionDuplicatedException(final SurveyQuestionDuplicatedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
