package com.atwoz.survey.infrastructure;

import com.atwoz.survey.domain.Survey;
import com.atwoz.survey.domain.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SurveyRepositoryImpl implements SurveyRepository {

    private final SurveyJpaRepository surveyJpaRepository;

    @Override
    public Survey save(final Survey survey) {
        return surveyJpaRepository.save(survey);
    }

    @Override
    public boolean isExistedByName(final String name) {
        return surveyJpaRepository.existsByName(name);
    }
}
