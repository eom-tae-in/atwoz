package com.atwoz.member.fixture.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.vo.MemberGrade;
import com.atwoz.member.domain.member.vo.MemberRole;
import com.atwoz.member.domain.member.vo.MemberStatus;

import static com.atwoz.member.fixture.member.MemberProfileDtoFixture.회원_프로필_DTO_요청;

@SuppressWarnings("NonAsciiCharacters")
public class MemberFixture {

    public static Member 일반_유저_생성() {
        Member member = Member.builder()
                .nickname("nickname")
                .phoneNumber("01011111111")
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.MEMBER)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();

        member.initializeWith(member.getNickname(), member.getRecommenderId(), 회원_프로필_DTO_요청());
        return member;
    }

    public static Member 일반_유저_생성(final int birthYear, final String phoneNumber) {
        Member member = Member.builder()
                .nickname("nickname")
                .phoneNumber(phoneNumber)
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.MEMBER)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();

        member.initializeWith(member.getNickname(), member.getRecommenderId(), 회원_프로필_DTO_요청(birthYear));
        return member;
    }

    public static Member 일반_유저_생성(final String nickname, final String phoneNumber) {
        Member member = Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.MEMBER)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();

        member.initializeWith(member.getNickname(), member.getRecommenderId(), 회원_프로필_DTO_요청());
        return member;
    }

    public static Member OAuth_인증만_완료한_유저_생성() {
        return Member.createWithOAuth("01011111111");
    }

    public static Member PASS_인증만_완료한_유저_생성() {
        return Member.createWithPass("남성", "01011111111");
    }

    public static Member 어드민_유저_생성() {
        return Member.builder()
                .nickname("nickname")
                .phoneNumber("01011111111")
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.ADMIN)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();
    }
}
