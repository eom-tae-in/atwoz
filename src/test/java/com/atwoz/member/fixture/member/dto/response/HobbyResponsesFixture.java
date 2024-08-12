package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyResponses;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyResponsesFixture {

    private static final int DEFAULT_NOW_PAGE = 0;
    private static final int DEFAULT_NEXT_PAGE = 1;
    private static final int DEFAULT_TOTAL_PAGES = 2;
    private static final long DEFAULT_TOTAL_ELEMENTS = 3;

    public static HobbyResponses 취미_응답_목록_생성_취미응답목록(final List<HobbyResponse> hobbyResponses) {
        return HobbyResponses.builder()
                .hobbyResponses(hobbyResponses)
                .nowPage(DEFAULT_NOW_PAGE)
                .nextPage(DEFAULT_NEXT_PAGE)
                .totalPages(DEFAULT_TOTAL_PAGES)
                .totalElements(DEFAULT_TOTAL_ELEMENTS)
                .build();
    }
}
