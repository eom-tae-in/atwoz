package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Graduate;
import com.atwoz.member.domain.member.profile.vo.Job;
import com.atwoz.member.domain.member.profile.vo.Location;
import com.atwoz.member.domain.member.profile.vo.Mbti;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberHobbiesFixture.회원_취미_목록_생성;
import static com.atwoz.member.fixture.member.domain.MemberHobbiesFixture.회원_취미_목록_생성_취미목록;
import static com.atwoz.member.fixture.member.domain.MemberStylesFixture.회원_스타일_목록_생성;
import static com.atwoz.member.fixture.member.domain.MemberStylesFixture.회원_스타일_목록_생성_스타일목록;
import static com.atwoz.member.fixture.member.domain.PhysicalProfileFixture.신체_프로필_생성;
import static com.atwoz.member.fixture.member.domain.PhysicalProfileFixture.신체_프로필_생성_생년월일;
import static com.atwoz.member.fixture.member.domain.PhysicalProfileFixture.신체_프로필_생성_성별;

@SuppressWarnings("NonAsciiCharacters")
public class ProfileFixture {

    private static final Job DEFAULT_JOB = Job.RESEARCH_AND_DEVELOP;
    private static final Graduate DEFAULT_GRADUATE = Graduate.SEOUL_FOURTH;
    private static final Smoke DEFAULT_SMOKE = Smoke.NEVER;
    private static final Drink DEFAULT_DRINK = Drink.NEVER;
    private static final Religion DEFAULT_RELIGION = Religion.NONE;
    private static final Mbti DEFAULT_MBTI = Mbti.ENFP;
    private static final ProfileAccessStatus DEFAULT_PROFILE_ACCESS_STATUS = ProfileAccessStatus.WAITING;
    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";

    public static Profile 프로필_생성() {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성())
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_생년월일(final int birthYear) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_생년월일(birthYear))
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_성별(final Gender gender) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_종교_성별(final Religion religion, final Gender gender) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(religion)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_도시_구역_성별(
            final String city,
            final String sector,
            final Gender gender
    ) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(city, sector))
                .build();
    }

    public static Profile 프로필_생성_성별_취미목록_스타일목록(final Gender gender, final List<Hobby> hobbies, final List<Style> styles) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_성별_취미_목록(final Gender gender, final List<Hobby> hobbies) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_취미목록_스타일목록(final List<Hobby> hobbies, final List<Style> styles) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성())
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_프로필접근상태_성별(final ProfileAccessStatus profileAccessStatus, final Gender gender) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(profileAccessStatus)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성())
                .memberStyles(회원_스타일_목록_생성())
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_생년월일_취미목록_스타일목록(
            final int birthYear,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .physicalProfile(신체_프로필_생성_생년월일(birthYear))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_프로필접근상태_성별_취미목록_스타일목록(
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(profileAccessStatus)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_프로필접근상태_성별_종교_취미목록_스타일목록(
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final Religion religion,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(religion)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(profileAccessStatus)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(DEFAULT_CITY, DEFAULT_SECTOR))
                .build();
    }

    public static Profile 프로필_생성_프로필접근상태_성별_도시_구역_취미목록_스타일목록(
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final String city,
            final String sector,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Profile.builder()
                .job(DEFAULT_JOB)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatus(profileAccessStatus)
                .physicalProfile(신체_프로필_생성_성별(gender))
                .memberHobbies(회원_취미_목록_생성_취미목록(hobbies))
                .memberStyles(회원_스타일_목록_생성_스타일목록(styles))
                .location(new Location(city, sector))
                .build();
    }
}
