package com.atwoz.member.fixture.member.dto.response.style;

import com.atwoz.member.application.member.profile.style.dto.StylePagingResponses;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.infrastructure.member.profile.style.dto.StylePagingResponse;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleSingleResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 스타일_응답_픽스처 {

    public static class 스타일_단건_조회_응답_픽스처 {

        private static final String DEFAULT_STYLE_NAME = "style";
        private static final String DEFAULT_STYLE_CODE = "code";

        public static StyleSingleResponse 스타일_단건_조회_응답_생성() {
            return new StyleSingleResponse(
                    DEFAULT_STYLE_NAME,
                    DEFAULT_STYLE_CODE
            );
        }

        public static StyleSingleResponse 스타일_단건_조회_응답_생성_스타일(final Style style) {
            return new StyleSingleResponse(
                    style.getName(),
                    style.getCode()
            );
        }
    }

    public static class 스타일_페이징_조회_응답_픽스처 {

        private static final long DEFAULT_HOBBY_ID = 1L;
        private static final String DEFAULT_STYLE_NAME = "style";
        private static final String DEFAULT_STYLE_CODE = "code";
        private static final int DEFAULT_NOW_PAGE = 0;
        private static final int DEFAULT_NEXT_PAGE = 1;
        private static final int DEFAULT_TOTAL_PAGES = 2;
        private static final long DEFAULT_TOTAL_ELEMENTS = 3;

        public static StylePagingResponse 스타일_페이징_조회_응답_생성() {
            return new StylePagingResponse(
                    DEFAULT_HOBBY_ID,
                    DEFAULT_STYLE_NAME,
                    DEFAULT_STYLE_CODE
            );
        }

        public static StylePagingResponse 스타일_페이징_조회_응답_생성_스타일(final Style style) {
            return new StylePagingResponse(
                    style.getId(),
                    style.getName(),
                    style.getCode()
            );
        }

        public static StylePagingResponses 스타일_페이징_조회_목록_응답_생성_스타일페이징목록응답(final List<StylePagingResponse> responses) {
            return StylePagingResponses.builder()
                    .stylePagingResponses(responses)
                    .nowPage(DEFAULT_NOW_PAGE)
                    .nextPage(DEFAULT_NEXT_PAGE)
                    .totalPages(DEFAULT_TOTAL_PAGES)
                    .totalElements(DEFAULT_TOTAL_ELEMENTS)
                    .build();
        }
    }
}
