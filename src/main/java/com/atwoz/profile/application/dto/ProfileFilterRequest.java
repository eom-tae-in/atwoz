package com.atwoz.profile.application.dto;

import java.util.Collections;
import java.util.List;

public record ProfileFilterRequest(
        Integer minAge,
        Integer maxAge,
        String smoke,
        String drink,
        String religion,
        String hobbyCode,
        List<String> cities
) {

    public ProfileFilterRequest(
            final Integer minAge,
            final Integer maxAge,
            final String smoke,
            final String drink,
            final String religion,
            final String hobbyCode,
            final List<String> cities
    ) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.smoke = smoke;
        this.drink = drink;
        this.religion = religion;
        this.hobbyCode = hobbyCode;
        this.cities = filterCities(cities);
    }

    private static List<String> filterCities(final List<String> cities) {
        if (isInvalidCase(cities)) {
            return Collections.emptyList();
        }

        return cities;
    }

    private static boolean isInvalidCase(final List<String> cities) {
        return cities == null ||
                cities.isEmpty() ||
                cities.stream()
                        .allMatch(city -> city == null || city.isBlank());
    }
}
