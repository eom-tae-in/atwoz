package com.atwoz.survey.fixture;

import com.atwoz.profile.domain.Profile;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

public class SurveySoulmateResponseFixture {

    public static SurveySoulmateResponse 소울메이트_응답() {
        return new SurveySoulmateResponse(1L, "nickname", "서울시", "강남구", 25);
    }

    public static SurveySoulmateResponse 소울메이트_응답_프로필(final Profile profile) {
        return new SurveySoulmateResponse(
                profile.getMemberId(),
                profile.getNickname(),
                profile.getLocation().getCity(),
                profile.getLocation().getSector(),
                profile.getPhysicalProfile().getAge()
        );
    }
}
