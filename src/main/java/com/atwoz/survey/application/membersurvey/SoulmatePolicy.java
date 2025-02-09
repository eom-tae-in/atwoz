package com.atwoz.survey.application.membersurvey;

import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

import java.util.List;

public interface SoulmatePolicy {

    List<SurveySoulmateResponse> extractSoulmatesWithPolicy(final List<SurveySoulmateResponse> responses);
}
