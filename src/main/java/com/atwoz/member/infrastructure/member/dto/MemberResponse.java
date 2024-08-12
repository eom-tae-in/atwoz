package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberResponse(
        String nickname,
        String phoneNumber,
        int age,
        int height,
        String gender,
        String job,
        String city,
        String sector,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        List<String> hobbyCodes,
        List<String> styleCodes
) {

    public static MemberResponse of(final Member member,
                                    final List<Hobby> hobbies,
                                    final List<Style> styles) {
        Profile profile = member.getMemberProfile()
                .getProfile();
        PhysicalProfile physicalProfile = profile.getPhysicalProfile();

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
                .hobbyCodes(hobbies.stream()
                        .map(Hobby::getCode)
                        .toList())
                .styleCodes(styles.stream()
                        .map(Style::getCode)
                        .toList())
                .build();
    }
}
