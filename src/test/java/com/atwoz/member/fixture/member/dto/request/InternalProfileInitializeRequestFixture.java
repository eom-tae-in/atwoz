package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.domain.member.dto.initial.InternalProfileInitializeRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;

import static com.atwoz.member.fixture.member.dto.request.InternalHobbiesInitializeRequestFixture.내부_취미_목록_초기화_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.InternalPhysicalProfileInitializeRequestFixture.내부_신체_프로필_초기화_요청_생성_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalStylesInitializeRequestFixture.내부_스타일_목록_초기화_요청_생성;

@SuppressWarnings("NonAsciiCharacters")
public class InternalProfileInitializeRequestFixture {

    private static final String DEFAULT_JOB_CODE = Job.RESEARCH_AND_DEVELOP.getCode();
    private static final String DEFAULT_GRADUATE_TYPE = Graduate.SEOUL_FOURTH.getName();
    private static final String DEFAULT_SMOKE_TYPE = Smoke.NEVER.getName();
    private static final String DEFAULT_DRINK_TYPE = Drink.NEVER.getName();
    private static final String DEFAULT_RELIGION_TYPE = Religion.NONE.getName();
    private static final String DEFAULT_MBTI_TYPE = Mbti.ENFP.toString();
    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";

    public static InternalProfileInitializeRequest 내부_프로필_초기화_요청_생성_연도관리자(final YearManager yearManager) {
        return InternalProfileInitializeRequest.builder()
                .internalPhysicalProfileInitializeRequest(내부_신체_프로필_초기화_요청_생성_연도관리자(yearManager))
                .internalHobbiesInitializeRequest(내부_취미_목록_초기화_요청_생성())
                .internalStylesInitializeRequest(내부_스타일_목록_초기화_요청_생성())
                .job(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE_TYPE)
                .smoke(DEFAULT_SMOKE_TYPE)
                .drink(DEFAULT_DRINK_TYPE)
                .religion(DEFAULT_RELIGION_TYPE)
                .mbti(DEFAULT_MBTI_TYPE)
                .city(DEFAULT_CITY)
                .sector(DEFAULT_SECTOR)
                .build();
    }
}
