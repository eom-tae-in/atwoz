package com.atwoz.survey.exception.membersurvey;

import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAlreadySubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyAnswerInvalidSubmitException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionNotSubmittedException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveySubmitDuplicateException;
import com.atwoz.survey.exception.membersurvey.exceptions.SurveyQuestionSubmitSizeNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberSurveyExceptionHandler {

    @ExceptionHandler(SurveyAlreadySubmittedException.class)
    public ResponseEntity<String> handleSurveyAlreadySubmittedException(final SurveyAlreadySubmittedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveyAnswerInvalidSubmitException.class)
    public ResponseEntity<String> handleSurveyAnswerInvalidSubmitException(final SurveyAnswerInvalidSubmitException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveyQuestionNotSubmittedException.class)
    public ResponseEntity<String> handleSurveyQuestionNotSubmittedException(final SurveyQuestionNotSubmittedException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveySubmitDuplicateException.class)
    public ResponseEntity<String> handleSurveyQuestionSubmitDuplicateException(final SurveySubmitDuplicateException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SurveyQuestionSubmitSizeNotMatchException.class)
    public ResponseEntity<String> handleSurveyQuestionSubmitSizeNotMatchException(final SurveyQuestionSubmitSizeNotMatchException exception) {
        return getExceptionWithStatus(exception, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<String> getExceptionWithStatus(final Exception exception, final HttpStatus status) {
        return ResponseEntity.status(status)
                .body(exception.getMessage());
    }
}
