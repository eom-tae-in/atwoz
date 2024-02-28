package com.atwoz.member.domain.info.option;

import com.atwoz.member.exception.exceptions.info.option.InvalidReligionException;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum Religion {

    CATHOLIC("천주교"),
    CHRIST("기독교"),
    BUDDHA("불교"),
    NONE("무교"),
    ETC("기타");

    private final String name;

    Religion(final String name) {
        this.name = name;
    }

    public static Religion findByName(final String name) {
        return Arrays.stream(values())
                .filter(religion -> name.equals(religion.getName()))
                .findFirst()
                .orElseThrow(InvalidReligionException::new);
    }
}
