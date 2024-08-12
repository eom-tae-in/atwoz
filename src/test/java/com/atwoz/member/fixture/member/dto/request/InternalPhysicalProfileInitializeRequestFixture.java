package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.initial.InternalPhysicalProfileInitializeRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;

@SuppressWarnings("NonAsciiCharacters")
public class InternalPhysicalProfileInitializeRequestFixture {

    private static final int DEFAULT_BIRTH_YEAR = 1990;
    private static final int DEFAULT_HEIGHT = 180;

    public static InternalPhysicalProfileInitializeRequest 내부_신체_프로필_초기화_요청_생성_연도관리자(final YearManager yearManager) {
        return InternalPhysicalProfileInitializeRequest.builder()
                .birthYear(DEFAULT_BIRTH_YEAR)
                .height(DEFAULT_HEIGHT)
                .yearManager(yearManager)
                .build();
    }

    public static InternalPhysicalProfileInitializeRequest 내부_신체_프로필_초기화_요청_생성_생년월일_연도관리자(final int birthYear,
                                                                                          final YearManager yearManager) {
        return InternalPhysicalProfileInitializeRequest.builder()
                .birthYear(birthYear)
                .height(DEFAULT_HEIGHT)
                .yearManager(yearManager)
                .build();
    }

    public static InternalPhysicalProfileInitializeRequest 내부_신체_프로필_초기화_요청_생성_키_연도관리자(final int height,
                                                                                       final YearManager yearManager) {
        return InternalPhysicalProfileInitializeRequest.builder()
                .birthYear(DEFAULT_BIRTH_YEAR)
                .height(height)
                .yearManager(yearManager)
                .build();
    }
}
