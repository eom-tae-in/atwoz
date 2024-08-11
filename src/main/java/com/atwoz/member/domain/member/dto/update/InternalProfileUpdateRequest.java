package com.atwoz.member.domain.member.dto.update;

import com.atwoz.member.application.member.dto.update.MemberUpdateRequest;
import com.atwoz.member.application.member.dto.update.ProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import java.util.List;
import lombok.Builder;

@Builder
public record InternalProfileUpdateRequest(
        InternalPhysicalProfileUpdateRequest internalPhysicalProfileUpdateRequest,
        InternalHobbiesUpdateRequest internalHobbiesUpdateRequest,
        InternalStylesUpdateRequest internalStylesUpdateRequest,
        String job,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        String city,
        String sector
) {

    public static InternalProfileUpdateRequest of(
            final MemberUpdateRequest memberUpdateRequest,
            final List<Hobby> hobbies,
            final List<Style> styles,
            final YearManager yearManager
    ) {
        ProfileUpdateRequest profileUpdateRequest = memberUpdateRequest.profileUpdateRequest();

        return InternalProfileUpdateRequest.builder()
                .internalPhysicalProfileUpdateRequest(InternalPhysicalProfileUpdateRequest.of(
                        memberUpdateRequest.getPhysicalProfileInitialRequest(),
                        yearManager
                ))
                .internalHobbiesUpdateRequest(InternalHobbiesUpdateRequest.from(hobbies))
                .internalStylesUpdateRequest(InternalStylesUpdateRequest.from(styles))
                .job(profileUpdateRequest.job())
                .graduate(profileUpdateRequest.graduate())
                .smoke(profileUpdateRequest.smoke())
                .drink(profileUpdateRequest.drink())
                .religion(profileUpdateRequest.religion())
                .mbti(profileUpdateRequest.mbti())
                .city(profileUpdateRequest.city())
                .sector(profileUpdateRequest.sector())
                .build();
    }
}
