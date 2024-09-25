package com.atwoz.member.fixture.member.dto.response.hobby;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyPagingResponses;
import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbySingleResponse;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class 취미_응답_픽스처 {

    public static class 취미_페이징_조회_응답_픽스처 {

        private static final long DEFAULT_HOBBY_ID = 1L;
        private static final String DEFAULT_HOBBY_NAME = "hobby";
        private static final String DEFAULT_HOBBY_CODE = "code";
        private static final int DEFAULT_NOW_PAGE = 0;
        private static final int DEFAULT_NEXT_PAGE = 1;
        private static final int DEFAULT_TOTAL_PAGES = 2;
        private static final long DEFAULT_TOTAL_ELEMENTS = 3;

        public static HobbyPagingResponse 취미_페이징_조회_응답_생성() {
            return new HobbyPagingResponse(
                    DEFAULT_HOBBY_ID,
                    DEFAULT_HOBBY_NAME,
                    DEFAULT_HOBBY_CODE
            );
        }

        public static HobbyPagingResponse 취미_페이징_조회_응답_생성_취미(final Hobby hobby) {
            return new HobbyPagingResponse(
                    hobby.getId(),
                    hobby.getName(),
                    hobby.getCode()
            );
        }

        public static HobbyPagingResponses 취미_페이징_조회_목록_응답_생성_취미페이징목록응답(final List<HobbyPagingResponse> responses) {
            return HobbyPagingResponses.builder()
                    .hobbyPagingResponses(responses)
                    .nowPage(DEFAULT_NOW_PAGE)
                    .nextPage(DEFAULT_NEXT_PAGE)
                    .totalPages(DEFAULT_TOTAL_PAGES)
                    .totalElements(DEFAULT_TOTAL_ELEMENTS)
                    .build();
        }
    }

    public static class 취미_단건_조회_응답_픽스처 {

        private static final String DEFAULT_HOBBY_NAME = "hobby";
        private static final String DEFAULT_HOBBY_CODE = "code";

        public static HobbySingleResponse 취미_단건_조회_응답_생성() {
            return new HobbySingleResponse(
                    DEFAULT_HOBBY_NAME,
                    DEFAULT_HOBBY_CODE
            );
        }

        public static HobbySingleResponse 취미_단건_조회_응답_생성_취미(final Hobby hobby) {
            return new HobbySingleResponse(
                    hobby.getName(),
                    hobby.getCode()
            );
        }
    }
}
