package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class InternalProfileFilterRequestFixture {

    private static final Integer DEFAULT_MIN_AGE = 25;
    private static final Integer DEFAULT_MAX_AGE = 35;
    private static final Smoke DEFAULT_SMOKE = Smoke.NEVER;
    private static final Drink DEFAULT_DRINK = Drink.NEVER;
    private static final Religion DEFAULT_RELIGION = Religion.NONE;
    private static final List<String> DEFAULT_CITIES = List.of("서울시");
    private static final String DEFAULT_HOBBY_CODE = "code1";

    public static InternalProfileFilterRequest 내부_프로필_필터_요청_생성() {
        return InternalProfileFilterRequest.builder()
                .minAge(DEFAULT_MIN_AGE)
                .maxAge(DEFAULT_MAX_AGE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .cities(DEFAULT_CITIES)
                .hobbyCode(DEFAULT_HOBBY_CODE)
                .build();
    }
}
