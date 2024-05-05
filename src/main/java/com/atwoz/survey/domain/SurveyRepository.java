package com.atwoz.survey.domain;

public interface SurveyRepository {

    Survey save(Survey survey);
    boolean isExistedByName(String name);
}
