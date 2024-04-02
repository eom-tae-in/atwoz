package com.atwoz.member.domain.member.profile;

import com.atwoz.member.exception.exceptions.member.profile.InvalidSmokeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Smoke {

    NEVER("비흡연"),
    NOT_NOW("금연 중"),
    ELECTRONIC("전자담배"),
    OFTEN("가끔 피움"),
    EVERYDAY("매일 피움");

    private final String name;

    Smoke(final String name) {
        this.name = name;
    }

    public static Smoke findByName(final String name) {
        return Arrays.stream(values())
                .filter(smoke -> name.equals(smoke.getName()))
                .findFirst()
                .orElseThrow(InvalidSmokeException::new);
    }
}
