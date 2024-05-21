package com.atwoz.survey.domain.survey;

import java.util.List;
import java.util.Optional;

public interface SurveyRepository {

    Survey save(Survey survey);
    boolean isExistedByName(String name);
    Optional<Survey> findById(Long id);
    List<Long> findAllRequiredSurveyIds();
}
