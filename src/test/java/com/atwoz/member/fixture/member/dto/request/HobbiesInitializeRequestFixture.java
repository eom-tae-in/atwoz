package com.atwoz.member.fixture.member.dto.request;

import com.atwoz.member.application.member.dto.initial.HobbiesInitializeRequest;
import java.util.List;

@SuppressWarnings("NonAsciiCharacters")
public class HobbiesInitializeRequestFixture {

    private static final List<String> DEFAULT_HOBBY_CODES = List.of("code1");

    public static HobbiesInitializeRequest 취미_목록_초기화_요청_생성() {
        return new HobbiesInitializeRequest(DEFAULT_HOBBY_CODES);
    }

    public static HobbiesInitializeRequest 취미_목록_초기화_요청_생성_취미코드목록(final List<String> hobbyCodes) {
        return new HobbiesInitializeRequest(hobbyCodes);
    }
}
