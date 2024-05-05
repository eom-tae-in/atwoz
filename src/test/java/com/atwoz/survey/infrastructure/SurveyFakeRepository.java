package com.atwoz.survey.infrastructure;

import com.atwoz.survey.domain.Survey;
import com.atwoz.survey.domain.SurveyRepository;

import java.util.HashMap;
import java.util.Map;

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
}
