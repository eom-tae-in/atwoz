package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.initial.ProfileInitializeRequest;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import static com.atwoz.member.fixture.member.dto.request.HobbiesInitializeRequestFixture.취미_목록_초기화_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.HobbiesInitializeRequestFixture.취미_목록_초기화_요청_생성_취미코드목록;
import static com.atwoz.member.fixture.member.dto.request.PhysicalProfileInitializeRequestFixture.신체_프로필_초기화_요청;
import static com.atwoz.member.fixture.member.dto.request.StylesInitializeRequestFixture.스타일_목록_초기화_요청;
import static com.atwoz.member.fixture.member.dto.request.StylesInitializeRequestFixture.스타일_목록_초기화_요청_취미코드목록;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class ProfileInitializeRequestFixture {

    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";
    private static final String DEFAULT_JOB_CODE = Job.RESEARCH_AND_DEVELOP.getCode();
    private static final String DEFAULT_GRADUATE_TYPE = Graduate.SEOUL_FOURTH.getName();
    private static final String DEFAULT_DRINK_TYPE = Drink.NEVER.getName();
    private static final String DEFAULT_SMOKE_TYPE = Smoke.NEVER.getName();
    private static final String DEFAULT_RELIGION_TYPE = Religion.NONE.getName();
    private static final String DEFAULT_MBTI_TYPE = Mbti.ENFP.toString();

    public static ProfileInitializeRequest 프로필_초기화_요청() {
        return new ProfileInitializeRequest(
                신체_프로필_초기화_요청(),
                취미_목록_초기화_요청_생성(),
                스타일_목록_초기화_요청(),
                DEFAULT_CITY,
                DEFAULT_SECTOR,
                DEFAULT_JOB_CODE,
                DEFAULT_GRADUATE_TYPE,
                DEFAULT_DRINK_TYPE,
                DEFAULT_SMOKE_TYPE,
                DEFAULT_RELIGION_TYPE,
                DEFAULT_MBTI_TYPE);
    }

    public static ProfileInitializeRequest 프로필_초기화_요청_취미코드목록_스타일코드목록(final List<String> hobbyCodes,
                                                                     final List<String> styleCodes) {
        return new ProfileInitializeRequest(
                신체_프로필_초기화_요청(),
                취미_목록_초기화_요청_생성_취미코드목록(hobbyCodes),
                스타일_목록_초기화_요청_취미코드목록(styleCodes),
                DEFAULT_CITY,
                DEFAULT_SECTOR,
                DEFAULT_JOB_CODE,
                DEFAULT_GRADUATE_TYPE,
                DEFAULT_DRINK_TYPE,
                DEFAULT_SMOKE_TYPE,
                DEFAULT_RELIGION_TYPE,
                DEFAULT_MBTI_TYPE);
    }
}
