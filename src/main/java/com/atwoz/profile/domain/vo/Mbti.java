package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidMbtiException;
import java.util.Arrays;

public enum Mbti {

    ISTJ,
    ISFJ,
    INFJ,
    INTJ,
    ISTP,
    ISFP,
    INFP,
    INTP,
    ESTP,
    ESFP,
    ENFP,
    ENTP,
    ESTJ,
    ESFJ,
    ENFJ,
    ENTJ;

    public static Mbti findByName(final String name) {
        return Arrays.stream(values())
                .filter(mbti -> name.equals(mbti.name()))
                .findFirst()
                .orElseThrow(InvalidMbtiException::new);
    }
}
