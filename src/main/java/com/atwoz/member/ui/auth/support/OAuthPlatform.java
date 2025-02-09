package com.atwoz.member.ui.auth.support;

import com.atwoz.member.exception.exceptions.auth.OAuthPlatformNotFountException;
import java.util.Arrays;

public enum OAuthPlatform {

    KAKAO("kakao"),
    GOOGLE("google");

    private final String name;

    OAuthPlatform(final String name) {
        this.name = name;
    }

    public static OAuthPlatform findPlatform(final String name) {

        return Arrays.stream(values())
                .filter(platform -> name.equals(platform.name))
                .findFirst()
                .orElseThrow(OAuthPlatformNotFountException::new);
    }
}
