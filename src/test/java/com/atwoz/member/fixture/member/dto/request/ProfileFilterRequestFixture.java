package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.ProfileCityFilterRequest;
import com.atwoz.member.application.member.dto.ProfileFilterRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class ProfileFilterRequestFixture {

    private static final String DEFAULT_CITY = "서울시";
    private static final Integer DEFAULT_MAX_AGE = 35;
    private static final Integer DEFAULT_MIN_AGE = 25;
    private static final String DEFAULT_SMOKE = "비흡연";
    private static final String DEFAULT_DRINK = "전혀 마시지 않음";
    private static final String DEFAULT_RELIGION = "무교";
    private static final String DEFAULT_HOBBY_CODE = "code1";

    public static ProfileFilterRequest 프로필_필터_요청서_생성() {
        return new ProfileFilterRequest(
                List.of(new ProfileCityFilterRequest(DEFAULT_CITY)),
                DEFAULT_MAX_AGE,
                DEFAULT_MIN_AGE,
                DEFAULT_SMOKE,
                DEFAULT_DRINK,
                DEFAULT_RELIGION,
                DEFAULT_HOBBY_CODE
        );
    }

    public static ProfileFilterRequest 프로필_필터_요청서_생성_취미코드(final String hobbyCode) {
        return new ProfileFilterRequest(
                List.of(new ProfileCityFilterRequest(DEFAULT_CITY)),
                DEFAULT_MAX_AGE,
                DEFAULT_MIN_AGE,
                DEFAULT_SMOKE,
                DEFAULT_DRINK,
                DEFAULT_RELIGION,
                hobbyCode
        );
    }
}
