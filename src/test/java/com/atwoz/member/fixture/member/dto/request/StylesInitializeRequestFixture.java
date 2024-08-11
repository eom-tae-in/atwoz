package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.initial.StylesInitializeRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class StylesInitializeRequestFixture {

    private static final List<String> DEFAULT_STYLE_CODES = List.of("code1");

    public static StylesInitializeRequest 스타일_목록_초기화_요청() {
        return new StylesInitializeRequest(DEFAULT_STYLE_CODES);
    }

    public static StylesInitializeRequest 스타일_목록_초기화_요청_취미코드목록(final List<String> styleCodes) {
        return new StylesInitializeRequest(styleCodes);
    }
}
