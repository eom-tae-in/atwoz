package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;

@SuppressWarnings("NonAsciiCharacters")
public class PhysicalProfileFixture {

    private static final int DEFAULT_AGE = 30;
    private static final int DEFAULT_HEIGHT = 180;
    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final int DEFAULT_CURRENT_YEAR = 2024;

    public static PhysicalProfile 신체_프로필_생성() {
        return PhysicalProfile.builder()
                .age(DEFAULT_AGE)
                .height(DEFAULT_HEIGHT)
                .gender(DEFAULT_GENDER)
                .build();
    }

    public static PhysicalProfile 신체_프로필_생성_생년월일(final int birthYear) {
        return PhysicalProfile.builder()
                .age(calculateAge(birthYear))
                .height(DEFAULT_HEIGHT)
                .gender(DEFAULT_GENDER)
                .build();
    }

    private static int calculateAge(final int birthYear) {
        return DEFAULT_CURRENT_YEAR - birthYear;
    }

    public static PhysicalProfile 신체_프로필_생성_성별(final Gender gender) {
        return PhysicalProfile.builder()
                .age(DEFAULT_AGE)
                .height(DEFAULT_HEIGHT)
                .gender(gender)
                .build();
    }
}
