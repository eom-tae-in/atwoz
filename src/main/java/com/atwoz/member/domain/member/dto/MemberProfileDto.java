package com.atwoz.member.domain.member.dto;

import com.atwoz.member.application.member.dto.ProfileInitializeRequest;
import com.atwoz.member.application.member.dto.ProfileUpdateRequest;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import java.util.List;
import lombok.Builder;

@Builder
public record MemberProfileDto(
        PhysicalProfileDto physicalProfileDto,
        HobbiesDto hobbiesdto,
        StylesDto stylesDto,
        String job,
        String graduate,
        String smoke,
        String drink,
        String religion,
        String mbti,
        String city,
        String sector
) {

    public static MemberProfileDto createWith(final ProfileInitializeRequest profileInitializeRequest,
                                              final YearManager yearManager) {
        return MemberProfileDto.builder()
                .physicalProfileDto(new PhysicalProfileDto(profileInitializeRequest.birthYear(),
                        profileInitializeRequest.height(), yearManager))
                .hobbiesdto(new HobbiesDto(profileInitializeRequest.hobbiesRequest().hobbies()))
                .stylesDto(new StylesDto(profileInitializeRequest.stylesRequest().styles()))
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

    public static MemberProfileDto createWith(final ProfileUpdateRequest profileUpdateRequest,
                                              final YearManager yearManager) {
        return MemberProfileDto.builder()
                .physicalProfileDto(new PhysicalProfileDto(profileUpdateRequest.birthYear(),
                        profileUpdateRequest.height(), yearManager))
                .hobbiesdto(new HobbiesDto(profileUpdateRequest.hobbiesRequest().hobbies()))
                .stylesDto(new StylesDto(profileUpdateRequest.stylesRequest().styles()))
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
        return hobbiesdto.hobbies();
    }

    public List<String> getStyles() {
        return stylesDto.styles();
    }
}
