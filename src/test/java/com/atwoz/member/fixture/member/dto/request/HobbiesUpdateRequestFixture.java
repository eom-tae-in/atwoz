package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.update.HobbiesUpdateRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class HobbiesUpdateRequestFixture {

    private static final List<String> DEFAULT_HOBBY_CODES = List.of("code4", "code5", "code6");

    public static HobbiesUpdateRequest 취미_목록_업데이트_요청() {
        return new HobbiesUpdateRequest(DEFAULT_HOBBY_CODES);
    }

    public static HobbiesUpdateRequest 취미_목록_업데이트_요청_취미코드목록(final List<String> hobbyCodes) {
        return new HobbiesUpdateRequest(hobbyCodes);
    }
}
