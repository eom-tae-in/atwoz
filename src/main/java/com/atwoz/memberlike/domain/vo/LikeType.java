package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeTypeNotFoundException;

import java.util.Arrays;

public enum LikeType {

    SMALL_LIKE("조금 관심있음"),
    MUCH_LIKE("많이 관심있음");

    private final String name;

    LikeType(final String name) {
        this.name = name;
    }

    public static LikeType findByName(final String name) {
        return Arrays.stream(values())
                .filter(likeType -> likeType.isSame(name))
                .findAny()
                .orElseThrow(LikeTypeNotFoundException::new);
    }

    private boolean isSame(final String name) {
        return name.equals(this.name);
    }
}
