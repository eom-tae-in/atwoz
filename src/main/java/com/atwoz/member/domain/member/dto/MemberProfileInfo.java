package com.atwoz.member.domain.member.dto;

import com.atwoz.member.application.member.dto.ProfileInitializeRequest;
import com.atwoz.member.application.member.dto.ProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberProfileInfo(
        PhysicalProfileInfo physicalProfileInfo,
        HobbiesInfo hobbiesInfo,
        StylesInfo stylesInfo,
        String job,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        String city,
        String sector
) {

    public static MemberProfileInfo createWith(final ProfileInitializeRequest profileInitializeRequest,
                                               final YearManager yearManager) {

        return MemberProfileInfo.builder()
                .physicalProfileInfo(new PhysicalProfileInfo(profileInitializeRequest.birthYear(),
                        profileInitializeRequest.height(), yearManager))
                .hobbiesInfo(new HobbiesInfo(profileInitializeRequest.hobbiesRequest().hobbies()))
                .stylesInfo(new StylesInfo(profileInitializeRequest.stylesRequest().styles()))
                .city(profileInitializeRequest.city())
                .sector(profileInitializeRequest.sector())
                .job(profileInitializeRequest.job())
                .graduate(profileInitializeRequest.graduate())
                .smoke(profileInitializeRequest.smoke())
                .drink(profileInitializeRequest.drink())
                .religion(profileInitializeRequest.religion())
                .mbti(profileInitializeRequest.mbti())
                .build();
    }

    public static MemberProfileInfo createWith(final ProfileUpdateRequest profileUpdateRequest,
                                               final YearManager yearManager) {

        return MemberProfileInfo.builder()
                .physicalProfileInfo(new PhysicalProfileInfo(profileUpdateRequest.birthYear(),
                        profileUpdateRequest.height(), yearManager))
                .hobbiesInfo(new HobbiesInfo(profileUpdateRequest.hobbiesRequest().hobbies()))
                .stylesInfo(new StylesInfo(profileUpdateRequest.stylesRequest().styles()))
                .city(profileUpdateRequest.city())
                .sector(profileUpdateRequest.sector())
                .job(profileUpdateRequest.job())
                .graduate(profileUpdateRequest.graduate())
                .smoke(profileUpdateRequest.smoke())
                .drink(profileUpdateRequest.drink())
                .religion(profileUpdateRequest.religion())
                .mbti(profileUpdateRequest.mbti())
                .build();
    }

    public List<String> getHobbies() {
        return hobbiesInfo.hobbies();
    }

    public List<String> getStyles() {
        return stylesInfo.styles();
    }
}
