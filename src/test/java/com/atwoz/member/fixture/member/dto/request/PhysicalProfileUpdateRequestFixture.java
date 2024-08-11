package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.update.PhysicalProfileUpdateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class PhysicalProfileUpdateRequestFixture {

    private static final int DEFAULT_BIRTH_YEAR = 2000;
    private static final int DEFAULT_HEIGHT = 185;

    public static PhysicalProfileUpdateRequest 신체_프로필_업데이트_요청() {
        return new PhysicalProfileUpdateRequest(
                DEFAULT_BIRTH_YEAR,
                DEFAULT_HEIGHT
        );
    }
}
