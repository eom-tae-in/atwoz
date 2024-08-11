package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.update.StylesUpdateRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class StylesUpdateRequestFixture {

    private static final List<String> DEFAULT_STYLE_CODES = List.of("code4", "code5", "code6");

    public static StylesUpdateRequest 스타일_목록_업데이트_요청() {
        return new StylesUpdateRequest(DEFAULT_STYLE_CODES);
    }

    public static StylesUpdateRequest 스타일_목록_업데이트_요청_스타일코드목록(final List<String> styleCodes) {
        return new StylesUpdateRequest(styleCodes);
    }
}
