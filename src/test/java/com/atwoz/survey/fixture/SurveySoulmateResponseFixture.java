package com.atwoz.survey.fixture;

import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

public class SurveySoulmateResponseFixture {

    public static SurveySoulmateResponse 소울메이트_응답() {
        return new SurveySoulmateResponse(1L, "nickname", "서울시", "강남구", 25);
    }
}
