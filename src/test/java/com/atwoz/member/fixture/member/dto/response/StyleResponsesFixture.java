package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.application.member.profile.style.dto.StyleResponses;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class StyleResponsesFixture {

    private static final int DEFAULT_NOW_PAGE = 0;
    private static final int DEFAULT_NEXT_PAGE = 1;
    private static final int DEFAULT_TOTAL_PAGES = 2;
    private static final long DEFAULT_TOTAL_ELEMENTS = 3;

    public static StyleResponses 스타일_응답_목록_생성_스타일응답목록(final List<StyleResponse> styleResponses) {
        return StyleResponses.builder()
                .styleResponses(styleResponses)
                .nowPage(DEFAULT_NOW_PAGE)
                .nextPage(DEFAULT_NEXT_PAGE)
                .totalPages(DEFAULT_TOTAL_PAGES)
                .totalElements(DEFAULT_TOTAL_ELEMENTS)
                .build();
    }
}
