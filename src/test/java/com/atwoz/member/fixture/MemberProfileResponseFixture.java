package com.atwoz.member.fixture;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.infrastructure.member.dto.MemberProfileResponse;

@SuppressWarnings("NonAsciiCharacters")
public class MemberProfileResponseFixture {

    public static MemberProfileResponse 회원_프로필_응답서_요청(final Member member) {
        Profile profile = getProfile(member);
        PhysicalProfile physicalProfile = getPhysicalProfile(member);

        return MemberProfileResponse.createWith(
                member.getNickname(),
                member.getPhoneNumber(),
                profile.getJob(),
                profile.getLocation(),
                profile.getGraduate(),
                profile.getSmoke(),
                profile.getDrink(),
                profile.getReligion(),
                profile.getMbti(),
                physicalProfile.getAge(),
                physicalProfile.getHeight(),
                physicalProfile.getGender()
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
