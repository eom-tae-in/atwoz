package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeIconNotFoundException;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum LikeIcon {

    NONE("아이콘 없음"),
    MESSAGE("메시지 수신됨"),
    HEART("매칭됨");

    private final String name;

    LikeIcon(final String name) {
        this.name = name;
    }

    public static LikeIcon findByName(final String name) {
        return Arrays.stream(values())
                .filter(likeIcon -> likeIcon.isSame(name))
                .findAny()
                .orElseThrow(LikeIconNotFoundException::new);
    }

    private boolean isSame(final String name) {
        return name.equals(this.name);
    }
}
