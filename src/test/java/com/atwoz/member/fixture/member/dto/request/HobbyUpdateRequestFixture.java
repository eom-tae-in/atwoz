package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.profile.hobby.dto.HobbyUpdateRequest;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyUpdateRequestFixture {

    private static final String DEFAULT_HOBBY_NAME = "hobby";
    private static final String DEFAULT_HOBBY_CODE = "code";

    public static HobbyUpdateRequest 취미_업데이트_요청_생성() {
        return new HobbyUpdateRequest(DEFAULT_HOBBY_NAME, DEFAULT_HOBBY_CODE);
    }

    public static HobbyUpdateRequest 취미_업데이트_요청_생성_이름_코드(final String hobbyName, final String hobbyCode) {
        return new HobbyUpdateRequest(hobbyName, hobbyCode);
    }
}
