package com.atwoz.mission.domain.vo;

import com.atwoz.mission.exception.PublicOptionInvalidException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum PublicOption {

    PUBLIC("public", "공개"),
    PRIVATE("private", "비공개");

    private final String name;
    private final String value;

    public static PublicOption from(final String publicOption) {
        String option = publicOption.toLowerCase();

        return Arrays.stream(values())
                .filter(it -> it.isSame(option))
                .findAny()
                .orElseThrow(PublicOptionInvalidException::new);
    }

    public boolean isSame(final String option) {
        return this.name.equals(option);
    }
}
