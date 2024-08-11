package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.update.InternalPhysicalProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;

public class InternalPhysicalProfileUpdateRequestFixture {

    private static final int DEFAULT_BIRTH_YEAR = 1990;
    private static final int DEFAULT_HEIGHT = 180;

    public static InternalPhysicalProfileUpdateRequest 내부_신체_프로필_업데이트_요청_생성_연도관리자(final YearManager yearManager) {
        return InternalPhysicalProfileUpdateRequest.builder()
                .birthYear(DEFAULT_BIRTH_YEAR)
                .height(DEFAULT_HEIGHT)
                .yearManager(yearManager)
                .build();
    }
}
