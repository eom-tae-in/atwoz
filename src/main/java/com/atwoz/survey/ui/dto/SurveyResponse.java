package com.atwoz.survey.ui.dto;

import com.atwoz.survey.domain.Survey;

public record SurveyResponse(
        String name,
        Boolean required
) {

    public static SurveyResponse from(final Survey survey) {
        return new SurveyResponse(survey.getName(), survey.getRequired());
    }
}
