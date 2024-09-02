package com.atwoz.interview.domain.interview.vo;

import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum InterviewType {

    ME("나"),
    RELATIONSHIP("관계"),
    COUPLE("연인");

    private final String name;

    InterviewType(final String name) {
        this.name = name;
    }

    public static InterviewType findByName(final String name) {
        return Arrays.stream(values())
                .filter(type -> type.isSame(name))
                .findAny()
                .orElseThrow(InterviewTypeNotFoundException::new);
    }

    private boolean isSame(final String name) {
        return name.equals(this.name);
    }
}
