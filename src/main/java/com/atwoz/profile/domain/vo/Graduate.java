package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidGraduateException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Graduate {

    SEOUL_FOURTH("서울 4년제"),
    ETC_FOURTH("지방 4년제"),
    PROFESSIONAL("전문대"),
    FOREIGN("해외대"),
    MASTER("석사"),
    DOCTOR("박사"),
    LAW_SCHOOL("로스쿨"),
    HIGH_SCHOOL("고등학교 졸업"),
    ETC("기타");

    private final String name;

    Graduate(final String name) {
        this.name = name;
    }

    public static Graduate findByName(final String name) {
        return Arrays.stream(values())
                .filter(graduate -> name.equals(graduate.getName()))
                .findFirst()
                .orElseThrow(InvalidGraduateException::new);
    }
}
