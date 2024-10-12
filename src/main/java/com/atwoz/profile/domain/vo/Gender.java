package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidGenderException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Gender {

    MALE("남성"),
    FEMALE("여성");

    private final String name;

    Gender(final String name) {
        this.name = name;
    }

    public static Gender findByName(final String name) {
        return Arrays.stream(values())
                .filter(gender -> name.equals(gender.getName()))
                .findFirst()
                .orElseThrow(InvalidGenderException::new);
    }
}
