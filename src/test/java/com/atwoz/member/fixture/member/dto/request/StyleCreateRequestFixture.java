package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class StyleCreateRequestFixture {

    private static final String DEFAULT_STYLE_NAME = "style";
    private static final String DEFAULT_STYLE_CODE = "code";

    public static StyleCreateRequest 스타일_생성_요청_생성() {
        return new StyleCreateRequest(DEFAULT_STYLE_NAME, DEFAULT_STYLE_CODE);
    }

    public static StyleCreateRequest 스타일_생성_요청_생성_이름_코드(final String styleName, final String styleCode) {
        return new StyleCreateRequest(styleName, styleCode);
    }
}
