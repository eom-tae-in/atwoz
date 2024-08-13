package com.atwoz.survey.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.survey.infrastructure.membersurvey.dto.SurveySoulmateResponse;

public class SurveySoulmateResponseFixture {

    public static SurveySoulmateResponse 소울메이트_응답() {
        return new SurveySoulmateResponse(1L, "nickname", "서울시", "강남구", 25);
    }

    public static SurveySoulmateResponse 소울메이트_응답_회원(final Member member) {
        return new SurveySoulmateResponse(
                member.getId(),
                member.getNickname(),
                member.getMemberProfile().getProfile().getLocation().getCity(),
                member.getMemberProfile().getProfile().getLocation().getSector(),
                member.getMemberProfile().getProfile().getPhysicalProfile().getAge()
        );
    }
}
