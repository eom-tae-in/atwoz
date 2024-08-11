package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.Hobby;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyFixture {

    private static final String DEFAULT_NAME = "hobby1";
    private static final String DEFAULT_CODE = "code1";

    public static Hobby 취미_생성() {
        return Hobby.builder()
                .name(DEFAULT_NAME)
                .code(DEFAULT_CODE)
                .build();
    }

    public static Hobby 취미_생성_이름_코드(final String name, final String code) {
        return Hobby.builder()
                .name(name)
                .code(code)
                .build();
    }
}
