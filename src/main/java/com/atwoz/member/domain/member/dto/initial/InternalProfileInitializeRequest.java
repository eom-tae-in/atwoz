package com.atwoz.member.domain.member.dto.initial;

import com.atwoz.member.application.member.dto.initial.MemberInitializeRequest;
import com.atwoz.member.application.member.dto.initial.ProfileInitializeRequest;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import java.util.List;
import lombok.Builder;

@Builder
public record InternalProfileInitializeRequest(
        InternalPhysicalProfileInitializeRequest internalPhysicalProfileInitializeRequest,
        InternalHobbiesInitializeRequest internalHobbiesInitializeRequest,
        InternalStylesInitializeRequest internalStylesInitializeRequest,
        String job,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        String city,
        String sector
) {

    public static InternalProfileInitializeRequest of(
            final MemberInitializeRequest memberInitializeRequest,
            final List<Hobby> hobbies,
            final List<Style> styles,
            final YearManager yearManager
    ) {
        ProfileInitializeRequest profileInitializeRequest = memberInitializeRequest.profileInitializeRequest();

        return InternalProfileInitializeRequest.builder()
                .internalPhysicalProfileInitializeRequest(InternalPhysicalProfileInitializeRequest.of(
                        memberInitializeRequest.getPhysicalProfileInitialRequest(),
                        yearManager
                ))
                .internalHobbiesInitializeRequest(InternalHobbiesInitializeRequest.from(hobbies))
                .internalStylesInitializeRequest(InternalStylesInitializeRequest.from(styles))
                .job(profileInitializeRequest.job())
                .graduate(profileInitializeRequest.graduate())
                .smoke(profileInitializeRequest.smoke())
                .drink(profileInitializeRequest.drink())
                .religion(profileInitializeRequest.religion())
                .mbti(profileInitializeRequest.mbti())
                .city(profileInitializeRequest.city())
                .sector(profileInitializeRequest.sector())
                .build();
    }
}
