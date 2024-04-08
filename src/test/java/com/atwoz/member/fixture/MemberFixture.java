package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberGrade;
import com.atwoz.member.domain.member.MemberProfile;
import com.atwoz.member.domain.member.MemberRole;
import com.atwoz.member.domain.member.MemberStatus;

public class MemberFixture {

    public static Member 일반_유저_생성() {
        return Member.builder()
                .nickname("nickname")
                .phoneNumber("01011111111")
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.MEMBER)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();
    }

    public static Member 일반_유저_생성(final String nickname, final String phoneNumber) {
        return Member.builder()
                .nickname(nickname)
                .phoneNumber(phoneNumber)
                .memberStatus(MemberStatus.ACTIVE)
                .memberGrade(MemberGrade.SILVER)
                .memberRole(MemberRole.MEMBER)
                .memberProfile(MemberProfile.createWith("남성"))
                .build();
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
