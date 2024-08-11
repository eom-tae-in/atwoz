package com.atwoz.member.fixture.member.domain;

import com.atwoz.member.domain.member.profile.Style;

@SuppressWarnings("NonAsciiCharacters")
public class StyleFixture {

    private static final String DEFAULT_NAME = "style1";
    private static final String DEFAULT_CODE = "code1";

    public static Style 스타일_생성() {
        return Style.builder()
                .name(DEFAULT_NAME)
                .code(DEFAULT_CODE)
                .build();
    }

    public static Style 스타일_생성_이름_코드(final String name, final String code) {
        return Style.builder()
                .name(name)
                .code(code)
                .build();
    }
}
