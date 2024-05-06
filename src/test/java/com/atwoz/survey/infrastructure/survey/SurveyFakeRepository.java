package com.atwoz.survey.infrastructure.survey;

import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.SurveyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SurveyFakeRepository implements SurveyRepository {

    private final Map<Long, Survey> map = new HashMap<>();

    private Long id = 1L;

    @Override
    public Survey save(final Survey survey) {
        Survey newSurvey = Survey.builder()
                .id(id++)
                .name(survey.getName())
                .required(survey.getRequired())
                .questions(survey.getQuestions())
                .build();
        map.put(newSurvey.getId(), newSurvey);

        return newSurvey;
    }

    @Override
    public boolean isExistedByName(final String name) {
        return map.values()
                .stream()
                .anyMatch(survey -> survey.isSameName(name));
    }

    @Override
    public Optional<Survey> findById(final Long id) {
        return Optional.of(map.get(id));
    }

    @Override
    public List<Survey> findAllRequiredSurveys() {
        return map.values()
                .stream()
                .filter(Survey::isRequired)
                .toList();
    }
}
