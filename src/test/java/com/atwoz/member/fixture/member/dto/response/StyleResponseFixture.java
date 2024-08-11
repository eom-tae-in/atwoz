package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;

public class StyleResponseFixture {

    private static final String DEFAULT_STYLE_NAME = "style";
    private static final String DEFAULT_STYLE_CODE = "code";

    public static StyleResponse 스타일_응답_생성() {
        return StyleResponse.builder()
                .name(DEFAULT_STYLE_NAME)
                .code(DEFAULT_STYLE_CODE)
                .build();
    }

    public static StyleResponse 스타일_응답_생성_스타일(final Style style) {
        return StyleResponse.builder()
                .name(style.getName())
                .code(style.getCode())
                .build();
    }
}
