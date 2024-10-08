package com.atwoz.profile.fixture;

import com.atwoz.profile.domain.Profile;
import com.atwoz.profile.domain.UserHobbies;
import com.atwoz.profile.domain.UserHobby;
import com.atwoz.profile.domain.YearManager;
import com.atwoz.profile.domain.vo.Drink;
import com.atwoz.profile.domain.vo.Gender;
import com.atwoz.profile.domain.vo.Graduate;
import com.atwoz.profile.domain.vo.Location;
import com.atwoz.profile.domain.vo.Mbti;
import com.atwoz.profile.domain.vo.PhysicalProfile;
import com.atwoz.profile.domain.vo.ProfileAccessStatus;
import com.atwoz.profile.domain.vo.Religion;
import com.atwoz.profile.domain.vo.Smoke;
import com.atwoz.profile.infrastructure.FakeYearManager;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 프로필_픽스처 {

    private static final Long DEFAULT_MEMBER_ID = 1L;
    private static final Long DEFAULT_RECOMMENDER_ID = 1L;
    private static final String DEFAULT_NICKNAME = "nickname";
    private static final String DEFAULT_JOB_CODE = "jobCode";
    private static final Graduate DEFAULT_GRADUATE = Graduate.SEOUL_FOURTH;
    private static final Smoke DEFAULT_SMOKE = Smoke.NEVER;
    private static final Drink DEFAULT_DRINK = Drink.NEVER;
    private static final Religion DEFAULT_RELIGION = Religion.NONE;
    private static final Mbti DEFAULT_MBTI = Mbti.ENFJ;
    private static final ProfileAccessStatus DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN = ProfileAccessStatus.PUBLIC;
    private static final ProfileAccessStatus DEFAULT_PROFILE_ACCESS_STATUS_BY_USER = ProfileAccessStatus.PUBLIC;
    private static final int DEFAULT_BIRTH_YEAR = 2000;
    private static final int DEFAULT_HEIGHT = 180;
    private static final String DEFAULT_GENDER = "남성";
    private static final String DEFAULT_CITY = "서울시";
    private static final String DEFAULT_SECTOR = "강남구";
    private static final List<String> DEFAULT_USER_HOBBIES = List.of("code1");
    private static final YearManager DEFAULT_YEARMANAGER = new FakeYearManager();

    public static Profile 프로필_생성() {
        return Profile.builder()
                .memberId(DEFAULT_MEMBER_ID)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디(final Long memberId) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_생년월일(final Long memberId, final int birthYear) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        birthYear,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_성별(final Long memberId, final Gender gender) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        gender.getName(),
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_위치(final Long memberId, final Location location) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(location.getCity(), location.getSector()))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_종교(final Long memberId, final Religion religion) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(religion)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_취미목록(final Long memberId, final UserHobbies userHobbies) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        DEFAULT_GENDER,
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(userHobbies.getHobbies()
                        .stream()
                        .map(UserHobby::getHobbyCode)
                        .toList()
                ))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_성별_위치(
            final Long memberId,
            final Gender gender,
            final Location location
    ) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        gender.getName(),
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(location.getCity(), location.getSector()))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_성별_종교(
            final Long memberId,
            final Gender gender,
            final Religion religion
    ) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(religion)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        gender.getName(),
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(DEFAULT_USER_HOBBIES))
                .build();
    }

    public static Profile 프로필_생성_회원아이디_성별_취미목록(
            final Long memberId,
            final Gender gender,
            final UserHobbies userHobbies
    ) {
        return Profile.builder()
                .memberId(memberId)
                .recommenderId(DEFAULT_RECOMMENDER_ID)
                .nickname(DEFAULT_NICKNAME)
                .jobCode(DEFAULT_JOB_CODE)
                .graduate(DEFAULT_GRADUATE)
                .smoke(DEFAULT_SMOKE)
                .drink(DEFAULT_DRINK)
                .religion(DEFAULT_RELIGION)
                .mbti(DEFAULT_MBTI)
                .profileAccessStatusByAdmin(DEFAULT_PROFILE_ACCESS_STATUS_BY_ADMIN)
                .profileAccessStatusByUser(DEFAULT_PROFILE_ACCESS_STATUS_BY_USER)
                .physicalProfile(PhysicalProfile.createWith(
                        DEFAULT_BIRTH_YEAR,
                        DEFAULT_HEIGHT,
                        gender.getName(),
                        DEFAULT_YEARMANAGER
                ))
                .location(Location.createWith(DEFAULT_CITY, DEFAULT_SECTOR))
                .userHobbies(UserHobbies.createWith(userHobbies.getHobbies()
                        .stream()
                        .map(UserHobby::getHobbyCode)
                        .toList()
                ))
                .build();
    }
}
