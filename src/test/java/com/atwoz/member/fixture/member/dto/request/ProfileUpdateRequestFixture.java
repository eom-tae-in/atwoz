package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.update.ProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import java.util.List;

import static com.atwoz.member.fixture.member.dto.request.HobbiesUpdateRequestFixture.취미_목록_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.HobbiesUpdateRequestFixture.취미_목록_업데이트_요청_취미코드목록;
import static com.atwoz.member.fixture.member.dto.request.PhysicalProfileUpdateRequestFixture.신체_프로필_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.StylesUpdateRequestFixture.스타일_목록_업데이트_요청;
import static com.atwoz.member.fixture.member.dto.request.StylesUpdateRequestFixture.스타일_목록_업데이트_요청_스타일코드목록;

@SuppressWarnings("NonAsciiCharacters")
public class ProfileUpdateRequestFixture {

    private static final String DEFAULT_CITY = "경기도";
    private static final String DEFAULT_SECTOR = "용인시";
    private static final String DEFAULT_JOB_CODE = Job.SELF_BUSINESS.getCode();
    private static final String DEFAULT_GRADUATE_TYPE = Graduate.ETC_FOURTH.getName();
    private static final String DEFAULT_DRINK_TYPE = Drink.OFTEN.getName();
    private static final String DEFAULT_SMOKE_TYPE = Smoke.OFTEN.getName();
    private static final String DEFAULT_RELIGION_TYPE = Religion.BUDDHA.getName();
    private static final String DEFAULT_MBTI_TYPE = Mbti.INFP.toString();

    public static ProfileUpdateRequest 프로필_업데이트_요청() {
        return new ProfileUpdateRequest(
                신체_프로필_업데이트_요청(),
                취미_목록_업데이트_요청(),
                스타일_목록_업데이트_요청(),
                DEFAULT_CITY,
                DEFAULT_SECTOR,
                DEFAULT_JOB_CODE,
                DEFAULT_GRADUATE_TYPE,
                DEFAULT_DRINK_TYPE,
                DEFAULT_SMOKE_TYPE,
                DEFAULT_RELIGION_TYPE,
                DEFAULT_MBTI_TYPE
        );
    }

    public static ProfileUpdateRequest 프로필_업데이트_요청_취미코드목록_스타일코드목록(final List<String> hobbyCodes,
                                                                  final List<String> styleCodes) {
        return new ProfileUpdateRequest(
                신체_프로필_업데이트_요청(),
                취미_목록_업데이트_요청_취미코드목록(hobbyCodes),
                스타일_목록_업데이트_요청_스타일코드목록(styleCodes),
                DEFAULT_CITY,
                DEFAULT_SECTOR,
                DEFAULT_JOB_CODE,
                DEFAULT_GRADUATE_TYPE,
                DEFAULT_DRINK_TYPE,
                DEFAULT_SMOKE_TYPE,
                DEFAULT_RELIGION_TYPE,
                DEFAULT_MBTI_TYPE
        );
    }
}
