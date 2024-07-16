package com.atwoz.member.fixture.selfintro;

import com.atwoz.member.application.selfintro.dto.CityRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroCreateRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroFilterRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroUpdateRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class SelfIntroRequestFixture {

    private static final String CREATE_CONTENT = "안녕하세요~";
    private static final String UPDATE_CONTENT = "반가워요~";
    private static final int MIN_AGE = 20;
    private static final int MAX_AGE = 30;
    private static final String CITY = "서울시";
    private static final boolean IS_ONLY_OPPOSITE_GENDER = false;

    public static SelfIntroCreateRequest 셀프_소개글_생성_요청서() {
        return new SelfIntroCreateRequest(CREATE_CONTENT);
    }

    public static SelfIntroFilterRequest 셀프_소개글_필터_요청서() {
        return new SelfIntroFilterRequest(
                MIN_AGE,
                MAX_AGE,
                IS_ONLY_OPPOSITE_GENDER,
                List.of(선호_지역_요청서())
        );
    }

    private static CityRequest 선호_지역_요청서() {
        return new CityRequest(CITY);
    }

    public static SelfIntroUpdateRequest 셀프_소개글_수정_요청서() {
        return new SelfIntroUpdateRequest(UPDATE_CONTENT);
    }

    public static SelfIntroUpdateRequest 셀프_소개글_수정_요청서(final String content) {
        return new SelfIntroUpdateRequest(content);
    }
}
