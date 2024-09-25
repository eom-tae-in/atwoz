package com.atwoz.member.application.selfintro.dto;

import java.util.Collections;
import java.util.List;

public record SelfIntroFilterRequest(
        Integer minAge,
        Integer maxAge,
        Boolean isOnlyOppositeGender,
        List<String> cities
) {

    public SelfIntroFilterRequest(
            final Integer minAge,
            final Integer maxAge,
            final Boolean isOnlyOppositeGender,
            final List<String> cities
    ) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.isOnlyOppositeGender = isOnlyOppositeGender;
        this.cities = ensureCities(cities);
    }

    private List<String> ensureCities(final List<String> cities) {
        if (isInvalidCase(cities)) {
            return Collections.emptyList();
        }
        return cities;
    }

    private boolean isInvalidCase(final List<String> cities) {
        return cities == null ||
                cities.stream()
                        .allMatch(city -> city == null || city.isBlank());
    }
}
