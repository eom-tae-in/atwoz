package com.atwoz.survey.ui.membersurvey.dto;

import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

import java.util.List;

public record SurveySoulmateResponses(
        List<SurveySoulmateResponse> soulmates
) {

    public static SurveySoulmateResponses from(final List<SurveySoulmateResponse> soulmates) {
        return new SurveySoulmateResponses(soulmates);
    }
}
