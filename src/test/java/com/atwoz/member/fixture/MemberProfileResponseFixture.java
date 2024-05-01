package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.infrastructure.member.dto.MemberProfileResponse;

@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileResponseFixture {

    public static MemberProfileResponse 회원_프로필_응답서_요청(final Member member) {
        return MemberProfileResponse.createWith(
                member.getNickname(),
                member.getPhoneNumber(),
                getProfile(member).getJob(),
                getProfile(member).getLocation(),
                getProfile(member).getGraduate(),
                getProfile(member).getSmoke(),
                getProfile(member).getDrink(),
                getProfile(member).getReligion(),
                getProfile(member).getMbti(),
                getPhysicalProfile(member).getAge(),
                getPhysicalProfile(member).getHeight(),
                getPhysicalProfile(member).getGender()
        );
    }

    private static Profile getProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile();
    }

    private static PhysicalProfile getPhysicalProfile(final Member member) {
        return member.getMemberProfile()
                .getProfile()
                .getPhysicalProfile();
    }
}
