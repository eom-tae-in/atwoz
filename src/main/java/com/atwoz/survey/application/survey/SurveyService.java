package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Long addSurvey(final SurveyCreateRequest request) {
        validateIsAlreadyUsedName(request.name());

        Survey survey = surveyRepository.save(Survey.from(request));
        return survey.getId();
    }

    private void validateIsAlreadyUsedName(final String name) {
        if (surveyRepository.isExistedByName(name)) {
            throw new SurveyNameAlreadyExistException();
        }
    }
}
