package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.profile.vo.Religion;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_도시_구역_성별;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_생년월일_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_성별;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_성별_취미_목록;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_종교_성별;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_프로필접근상태_성별_도시_구역_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_프로필접근상태_성별_종교_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.ProfileFixture.프로필_생성_프로필접근상태_성별_취미목록_스타일목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileFixture {

    private static final ProfileAccessStatus DEFAULT_PROFILE_ACCESS_STATUS = ProfileAccessStatus.WAITING;

    public static MemberProfile 회원_프로필_생성() {
        return MemberProfile.builder()
                .profile(프로필_생성())
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_성별(final Gender gender) {
        return MemberProfile.builder()
                .profile(프로필_생성_성별(gender))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_종교_성별(final Religion religion, final Gender gender) {
        return MemberProfile.builder()
                .profile(프로필_생성_종교_성별(religion, gender))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_도시_구역_성별(
            final String city,
            final String sector,
            final Gender gender
    ) {
        return MemberProfile.builder()
                .profile(프로필_생성_도시_구역_성별(city, sector, gender))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_성별_취미목록(final Gender gender, final List<Hobby> hobbyCodes) {
        return MemberProfile.builder()
                .profile(프로필_생성_성별_취미_목록(gender, hobbyCodes))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_취미목록_스타일목록(final List<Hobby> hobbies, final List<Style> styles) {
        return MemberProfile.builder()
                .profile(프로필_생성_취미목록_스타일목록(hobbies, styles))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_생년월일_취미목록_스타일목록(
            final int birthYear,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return MemberProfile.builder()
                .profile(프로필_생성_생년월일_취미목록_스타일목록(birthYear, hobbies, styles))
                .profileAccessStatus(DEFAULT_PROFILE_ACCESS_STATUS)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록(
            final ProfileAccessStatus memberProfileAccessStatus,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final Religion religion,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return MemberProfile.builder()
                .profile(프로필_생성_프로필접근상태_성별_종교_취미목록_스타일목록(
                        profileAccessStatus,
                        gender,
                        religion,
                        hobbies,
                        styles
                ))
                .profileAccessStatus(memberProfileAccessStatus)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_도시_구역_취미목록_스타일목록(
            final ProfileAccessStatus memberProfileAccessStatus,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final String city,
            final String sector,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return MemberProfile.builder()
                .profile(프로필_생성_프로필접근상태_성별_도시_구역_취미목록_스타일목록(
                        profileAccessStatus,
                        gender,
                        city,
                        sector,
                        hobbies,
                        styles
                ))
                .profileAccessStatus(memberProfileAccessStatus)
                .build();
    }

    public static MemberProfile 회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
            final ProfileAccessStatus memberProfileAccessStatue,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return MemberProfile.builder()
                .profile(프로필_생성_프로필접근상태_성별_취미목록_스타일목록(profileAccessStatus, gender, hobbies, styles))
                .profileAccessStatus(memberProfileAccessStatue)
                .build();
    }
}
