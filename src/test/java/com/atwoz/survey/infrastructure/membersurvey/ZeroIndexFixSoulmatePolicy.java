package com.atwoz.survey.infrastructure.membersurvey;

import com.atwoz.survey.application.membersurvey.SoulmatePolicy;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

import java.util.List;

public class ZeroIndexFixSoulmatePolicy implements SoulmatePolicy {

    @Override
    public List<SurveySoulmateResponse> extractSoulmatesWithPolicy(final List<SurveySoulmateResponse> responses) {
        return List.of(responses.get(0));
    }
}
