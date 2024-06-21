package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeLevelNotFoundException;

import java.util.Arrays;

public enum LikeLevel {

    DEFAULT_LIKE("관심있어요"),
    MUCH_LIKE("매우 관심있어요");

    private final String name;

    LikeLevel(final String name) {
        this.name = name;
    }

    public static LikeLevel findByName(final String name) {
        return Arrays.stream(values())
                .filter(likeType -> likeType.isSame(name))
                .findAny()
                .orElseThrow(LikeLevelNotFoundException::new);
    }

    private boolean isSame(final String name) {
        return name.equals(this.name);
    }
}
