package com.atwoz.alert.domain.vo;

import com.atwoz.alert.exception.exceptions.AlertGroupNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AlertGroup {

    MEMBER_LIKE("좋아요"),
    SELF_INTRODUCE("셀프소개"),
    INTERVIEW("인터뷰"),
    ALERT("알림");

    private String name;

    AlertGroup(final String name) {
        this.name = name;
    }

    public static AlertGroup findByName(final String name) {
        return Arrays.stream(values())
                .filter(group -> group.isSame(name))
                .findAny()
                .orElseThrow(AlertGroupNotFoundException::new);
    }

    private boolean isSame(final String name) {
        return name.equals(this.name);
    }
}
