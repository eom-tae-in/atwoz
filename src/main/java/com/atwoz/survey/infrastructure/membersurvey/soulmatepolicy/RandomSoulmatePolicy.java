package com.atwoz.survey.infrastructure.membersurvey.soulmatepolicy;

import com.atwoz.survey.application.membersurvey.soulmatepolicy.SoulmatePolicy;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomSoulmatePolicy implements SoulmatePolicy {

    @Override
    public List<SurveySoulmateResponse> extractSoulmatesWithPolicy(final List<SurveySoulmateResponse> responses) {
        Random randomModule = new Random();
        int index = randomModule.nextInt(responses.size());
        return List.of(responses.get(index));
    }
}
