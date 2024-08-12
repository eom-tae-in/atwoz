package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import com.atwoz.member.domain.member.profile.vo.ProfileAccessStatus;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.domain.member.vo.MemberStatus;
import java.time.LocalDate;
import java.util.List;

import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_도시_구역_성별;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_생년월일_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_성별;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_성별_취미목록;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_종교_성별;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_도시_구역_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록;
import static com.atwoz.member.fixture.member.domain.MemberProfileFixture.회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    private static final String DEFAULT_NICKNAME = "nickname";
    private static final String DEFAULT_PHONE_NUMBER = "01000000000";
    private static final MemberStatus DEFAULT_MEMBER_STATUS = MemberStatus.ACTIVE;
    private static final MemberGrade DEFAULT_MEMBER_GRADE = MemberGrade.SILVER;
    private static final LocalDate DEFAULT_LATEST_VISIT_DATE = LocalDate.now();

    public static Member 회원_생성() {
        return Member.builder()
                .nickname(DEFAULT_NICKNAME)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성())
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임(final String nickname) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성())
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_취미목록_스타일목록(final List<Hobby> hobbies,
                                          final List<Style> styles) {
        return Member.builder()
                .nickname(DEFAULT_NICKNAME)
                .phoneNumber(DEFAULT_PHONE_NUMBER)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_취미목록_스타일목록(hobbies, styles))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_성별(
            final String nickname,
            final String phoneNumber,
            final Gender gender
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_성별(gender))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_성별_취미목록(
            final String nickname,
            final String phoneNumber,
            final Gender gender,
            final List<Hobby> hobbies
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_성별_취미목록(gender, hobbies))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }


    public static Member 회원_생성_닉네임_전화번호_종교_성별(
            final String nickname,
            final String phoneNumber,
            final Religion religion,
            final Gender gender
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_종교_성별(religion, gender))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_도시_구역_성별(
            final String nickname,
            final String phoneNumber,
            final String city,
            final String sector,
            final Gender gender
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_도시_구역_성별(city, sector, gender))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원등급_성별(
            final String nickname,
            final String phoneNumber,
            final MemberGrade memberGrade,
            final Gender gender
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(memberGrade)
                .memberProfile(회원_프로필_생성_성별(gender))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_취미목록_스타일목록(hobbies, styles))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_생년월일_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final int birthYear,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_생년월일_취미목록_스타일목록(birthYear, hobbies, styles))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final ProfileAccessStatus memberProfileAccessStatus,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                        memberProfileAccessStatus,
                        profileAccessStatus,
                        gender,
                        hobbies,
                        styles
                ))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_방문날짜_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final ProfileAccessStatus memberProfileAccessStatue,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final LocalDate localDate,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                        memberProfileAccessStatue,
                        profileAccessStatus,
                        gender,
                        hobbies,
                        styles
                ))
                .latestVisitDate(localDate)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final ProfileAccessStatus memberProfileAccessStatus,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final Religion religion,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_종교_취미목록_스타일목록(
                        memberProfileAccessStatus,
                        profileAccessStatus,
                        gender,
                        religion,
                        hobbies,
                        styles
                ))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원프로필접근상태_프로필접근상태_성별_도시_지역_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final ProfileAccessStatus memberProfileAccessStatus,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final String city,
            final String sector,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(DEFAULT_MEMBER_GRADE)
                .memberProfile(회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_도시_구역_취미목록_스타일목록(
                        memberProfileAccessStatus,
                        profileAccessStatus,
                        gender,
                        city,
                        sector,
                        hobbies,
                        styles
                ))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }

    public static Member 회원_생성_닉네임_전화번호_회원등급_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
            final String nickname,
            final String phoneNumber,
            final MemberGrade memberGrade,
            final ProfileAccessStatus memberProfileAccessStatue,
            final ProfileAccessStatus profileAccessStatus,
            final Gender gender,
            final List<Hobby> hobbies,
            final List<Style> styles
    ) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(DEFAULT_MEMBER_STATUS)
                .memberGrade(memberGrade)
                .memberProfile(회원_프로필_생성_회원프로필접근상태_프로필접근상태_성별_취미목록_스타일목록(
                        memberProfileAccessStatue,
                        profileAccessStatus,
                        gender,
                        hobbies,
                        styles))
                .latestVisitDate(DEFAULT_LATEST_VISIT_DATE)
                .build();
    }
}
