package com.atwoz.member.fixture.member.dto.response;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;

@SuppressWarnings("NonAsciiCharacters")
public class HobbyResponseFixture {

    private static final String DEFAULT_HOBBY_NAME = "hobby";
    private static final String DEFAULT_HOBBY_CODE = "code";

    public static HobbyResponse 취미_응답_생성() {
        return HobbyResponse.builder()
                .name(DEFAULT_HOBBY_NAME)
                .code(DEFAULT_HOBBY_CODE)
                .build();
    }

    public static HobbyResponse 취미_응답_생성_취미(final Hobby hobby) {
        return HobbyResponse.builder()
                .name(hobby.getName())
                .code(hobby.getCode())
                .build();
    }
}
