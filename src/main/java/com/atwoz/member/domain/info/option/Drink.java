package com.atwoz.member.domain.info.option;

import com.atwoz.member.exception.exceptions.info.option.InvalidDrinkException;
import lombok.Getter;
import java.util.Arrays;

@Getter
public enum Drink {

    NEVER("전혀 마시지 않음"),
    SOCIETY("사회적 음주"),
    OFTEN("가끔 마심"),
    ENJOY("술자리를 즐김"),
    NOT_NOW("금주 중");

    private final String name;

    Drink(final String name) {
        this.name = name;
    }

    public static Drink findByName(final String name) {
        return Arrays.stream(values())
                .filter(drink -> name.equals(drink.getName()))
                .findFirst()
                .orElseThrow(InvalidDrinkException::new);
    }
}
