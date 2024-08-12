package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyCreateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyCreateRequestFixture {

    private static final String DEFAULT_HOBBY_NAME = "hobby";
    private static final String DEFAULT_HOBBY_CODE = "code";

    public static HobbyCreateRequest 취미_생성_요청_생성() {
        return new HobbyCreateRequest(DEFAULT_HOBBY_NAME, DEFAULT_HOBBY_CODE);
    }

    public static HobbyCreateRequest 취미_생성_요청_생성_이름_코드(final String hobbyName,
                                                       final String hobbyCode) {
        return new HobbyCreateRequest(hobbyName, hobbyCode);
    }
}
