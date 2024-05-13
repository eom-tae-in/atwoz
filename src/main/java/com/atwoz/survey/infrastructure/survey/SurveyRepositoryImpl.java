package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Optional<Survey> findById(final Long id) {
        return surveyJpaRepository.findById(id);
    }

    @Override
    public List<Long> findAllRequiredSurveyIds() {
        return surveyJpaRepository.findAllRequiredSurveyIds();
    }
}
