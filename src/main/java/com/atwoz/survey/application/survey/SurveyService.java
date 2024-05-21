package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.domain.survey.dto.SurveyCreateDto;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public Survey addSurvey(final SurveyCreateRequest request) {
        validateIsAlreadyUsedName(request.name());
        SurveyCreateDto surveyRequest = SurveyCreateDto.from(request);

        return surveyRepository.save(Survey.from(surveyRequest));
    }

    private void validateIsAlreadyUsedName(final String name) {
        if (surveyRepository.isExistedByName(name)) {
            throw new SurveyNameAlreadyExistException();
        }
    }
}
