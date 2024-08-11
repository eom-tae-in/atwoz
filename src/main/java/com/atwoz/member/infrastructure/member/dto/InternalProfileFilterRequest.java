package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.application.member.dto.ProfileCityFilterRequest;
import com.atwoz.member.application.member.dto.ProfileFilterRequest;
import com.atwoz.member.domain.member.profile.vo.Drink;
import com.atwoz.member.domain.member.profile.vo.Religion;
import com.atwoz.member.domain.member.profile.vo.Smoke;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import lombok.Builder;

@Builder
public record InternalProfileFilterRequest(
        Integer minAge,
        Integer maxAge,
        String hobbyCode,
        Smoke smoke,
        Drink drink,
        Religion religion,
        List<String> cities
) {

    public static InternalProfileFilterRequest from(final ProfileFilterRequest profileFilterRequest) {
        return InternalProfileFilterRequest.builder()
                .minAge(profileFilterRequest.minAge())
                .maxAge(profileFilterRequest.maxAge())
                .hobbyCode(profileFilterRequest.hobbyCode())
                .smoke(convertToEnum(profileFilterRequest.smoke(), Smoke::findByName))
                .drink(convertToEnum(profileFilterRequest.drink(), Drink::findByName))
                .religion(convertToEnum(profileFilterRequest.religion(), Religion::findByName))
                .cities(convertToCities(profileFilterRequest.profileCityFilterRequests()))
                .build();
    }

    private static <T> T convertToEnum(final String value, final Function<String, T> converter) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return converter.apply(value);
    }

    private static List<String> convertToCities(final List<ProfileCityFilterRequest> profileCityFilterRequests) {
        if (profileCityFilterRequests == null || profileCityFilterRequests.isEmpty()) {
            return Collections.emptyList();
        }

        return profileCityFilterRequests.stream()
                .map(ProfileCityFilterRequest::city)
                .toList();
    }
}







