package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.initial.PhysicalProfileInitialRequest;

@SuppressWarnings("NonAsciiCharacters")
public class PhysicalProfileInitializeRequestFixture {

    private static final int DEFAULT_BIRTH_YEAR = 1990;
    private static final int DEFAULT_HEIGHT = 180;

    public static PhysicalProfileInitialRequest 신체_프로필_초기화_요청() {
        return new PhysicalProfileInitialRequest(
                DEFAULT_BIRTH_YEAR,
                DEFAULT_HEIGHT
        );
    }
}
