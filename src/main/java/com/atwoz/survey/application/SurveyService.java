package com.atwoz.survey.application;

import com.atwoz.survey.application.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.Survey;
import com.atwoz.survey.domain.SurveyRepository;
import com.atwoz.survey.exception.exceptions.SurveyNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Survey addSurvey(final SurveyCreateRequest request) {
        validateIsAlreadyUsedName(request.surveyName());

        Survey survey = Survey.createWith(request);
        return surveyRepository.save(survey);
    }

    private void validateIsAlreadyUsedName(final String name) {
        if (surveyRepository.isExistedByName(name)) {
            throw new SurveyNameAlreadyExistException();
        }
    }
}
