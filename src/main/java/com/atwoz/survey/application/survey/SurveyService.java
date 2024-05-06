package com.atwoz.survey.application.survey;

import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import com.atwoz.survey.exception.survey.exceptions.SurveyNameAlreadyExistException;
import com.atwoz.survey.exception.survey.exceptions.SurveyNotFoundException;
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

        Survey survey = surveyRepository.save(Survey.createWith(request));
        return survey.getId();
    }

    private void validateIsAlreadyUsedName(final String name) {
        if (surveyRepository.isExistedByName(name)) {
            throw new SurveyNameAlreadyExistException();
        }
    }

    public void submitSurvey(final Long memberId, final Long surveyId, final SurveySubmitRequest request) {
        Survey survey = findSurveyById(surveyId);
        // request
            // List<SurveyQuestionSubmitRequest>
                // Long questionId
                // Long answerId
    }

    private Survey findSurveyById(final Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(SurveyNotFoundException::new);
    }
}
