package com.atwoz.hobby.fixture;

import com.atwoz.hobby.application.dto.HobbyCreateRequest;
import com.atwoz.hobby.application.dto.HobbyUpdateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class 취미_요청_픽스처 {

    public static class 취미_생성_요청_픽스처 {

        private static final String DEFAULT_HOBBY_NAME = "hobby";
        private static final String DEFAULT_HOBBY_CODE = "code";

        public static HobbyCreateRequest 취미_생성_요청_생성() {
            return new HobbyCreateRequest(DEFAULT_HOBBY_NAME, DEFAULT_HOBBY_CODE);
        }

        public static HobbyCreateRequest 취미_생성_요청_생성_이름_코드(final String hobbyName, final String hobbyCode) {
            return new HobbyCreateRequest(hobbyName, hobbyCode);
        }
    }

    public static class 취미_수정_요청_픽스처 {

        private static final String DEFAULT_HOBBY_NAME = "hobby";
        private static final String DEFAULT_HOBBY_CODE = "code";

        public static HobbyUpdateRequest 취미_업데이트_요청_생성() {
            return new HobbyUpdateRequest(DEFAULT_HOBBY_NAME, DEFAULT_HOBBY_CODE);
        }

        public static HobbyUpdateRequest 취미_업데이트_요청_생성_이름_코드(final String hobbyName, final String hobbyCode) {
            return new HobbyUpdateRequest(hobbyName, hobbyCode);
        }
    }
}
