package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.MemberHobbies;
import com.atwoz.member.domain.member.profile.MemberHobby;
import com.atwoz.member.domain.member.profile.MemberStyle;
import com.atwoz.member.domain.member.profile.MemberStyles;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class MemberResponseFixture {

    private static final String DEFAULT_NICKNAME = "nickname";
    private static final String DEFAULT_PHONE_NUMBER = "01011111111";
    private static final int DEFAULT_AGE = 30;
    private static final int DEFAULT_HEIGHT = 180;
    private static final String DEFAULT_GENDER = "남성";
    private static final String DEFAULT_JOB_NAME = Job.RESEARCH_AND_DEVELOP.getName();
    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";
    private static final String DEFAULT_GRADUATE_TYPE = Graduate.SEOUL_FOURTH.getName();
    private static final String DEFAULT_SMOKE_TYPE = Smoke.NEVER.getName();
    private static final String DEFAULT_DRINK_TYPE = Drink.NEVER.getName();
    private static final String DEFAULT_RELIGION_TYPE = Religion.NONE.getName();
    private static final String DEFAULT_MBTI_TYPE = Mbti.ENFP.toString();
    private static final List<String> DEFAULT_HOBBY_CODES = List.of("B001", "B002", "B003");
    private static final List<String> DEFAULT_STYLE_CODES = List.of("C001", "C002", "C003");

    public static MemberResponse 회원_조회_응답() {
        return MemberResponse.builder()
                .nickname(DEFAULT_NICKNAME)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .age(DEFAULT_AGE)
                .height(DEFAULT_HEIGHT)
                .gender(DEFAULT_GENDER)
                .job(DEFAULT_JOB_NAME)
                .city(DEFAULT_CITY)
                .sector(DEFAULT_SECTOR)
                .graduate(DEFAULT_GRADUATE_TYPE)
                .smoke(DEFAULT_SMOKE_TYPE)
                .drink(DEFAULT_DRINK_TYPE)
                .religion(DEFAULT_RELIGION_TYPE)
                .mbti(DEFAULT_MBTI_TYPE)
                .hobbyCodes(DEFAULT_HOBBY_CODES)
                .styleCodes(DEFAULT_STYLE_CODES)
                .build();
    }

    public static MemberResponse 회원_정보_응답서_요청(final Member member) {
        Profile profile = member.getMemberProfile()
                .getProfile();
        PhysicalProfile physicalProfile = profile.getPhysicalProfile();
        MemberHobbies memberHobbies = profile.getMemberHobbies();
        MemberStyles memberStyles = profile.getMemberStyles();

        return MemberResponse.builder()
                .nickname(member.getNickname())
                .phoneNumber(member.getPhoneNumber())
                .age(physicalProfile.getAge())
                .height(physicalProfile.getHeight())
                .gender(physicalProfile.getGender()
                        .getName())
                .job(profile.getJob()
                        .getName())
                .city(profile.getLocation()
                        .getCity())
                .sector(profile.getLocation()
                        .getSector())
                .graduate(profile.getGraduate()
                        .getName())
                .smoke(profile.getSmoke()
                        .getName())
                .drink(profile.getDrink()
                        .getName())
                .religion(profile.getReligion()
                        .getName())
                .mbti(profile.getMbti()
                        .name())
                .hobbyCodes(memberHobbies.getHobbies()
                        .stream()
                        .map(MemberHobby::getHobby)
                        .map(Hobby::getCode)
                        .toList())
                .styleCodes(memberStyles.getStyles()
                        .stream()
                        .map(MemberStyle::getStyle)
                        .map(Style::getCode)
                        .toList())
                .build();
    }
}
