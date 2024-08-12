package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class StyleUpdateRequestFixture {

    private static final String DEFAULT_STYLE_NAME = "style";
    private static final String DEFAULT_STYLE_CODE = "code";

    public static StyleUpdateRequest 스타일_업데이트_요청_생성() {
        return new StyleUpdateRequest(DEFAULT_STYLE_NAME, DEFAULT_STYLE_CODE);
    }

    public static StyleUpdateRequest 스타일_업데이트_요청_생성_이름_코드(final String styleName, final String styleCode) {
        return new StyleUpdateRequest(styleName, styleCode);
    }
}
