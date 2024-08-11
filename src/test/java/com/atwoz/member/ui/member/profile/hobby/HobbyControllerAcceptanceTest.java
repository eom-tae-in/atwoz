package com.atwoz.member.ui.member.profile.hobby;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.dto.request.HobbyCreateRequestFixture.취미_생성_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.HobbyUpdateRequestFixture.취미_업데이트_요청_생성;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyControllerAcceptanceTest extends HobbyControllerAcceptanceTestFixture {

    private static final String 취미_저장_URL = "/api/members/hobbies";
    private static final String 취미_단건_조회_URL = "/api/members/hobbies/1";
    private static final String 취미_페이징_조회_URL = "/api/members/hobbies";
    private static final String 취미_수정_URL = "/api/members/hobbies/1";
    private static final String 취미_삭제_URI = "/api/members/hobbies/2";

    private String 토큰;

    @BeforeEach
    void setup() {
        토큰 = 토큰_생성(회원_생성());
    }

    @Test
    void 취미_저장() {
        // given
        var 취미_생성_요청서 = 취미_생성_요청_생성();

        // when
        var 취미_저장_요청_결과 = 취미_저장_요청(취미_생성_요청서, 취미_저장_URL, 토큰);

        // then
        취미_저장_요청_검증(취미_저장_요청_결과);
    }

    @Test
    void 취미_단건_조회() {
        // when
        var 취미_단건_조회_요청_결과 = 취미_단건_조회_요청(취미_단건_조회_URL, 토큰);

        // then
        취미_단건_조회_요청_검증(취미_단건_조회_요청_결과);
    }

    @Test
    void 취미_페이징_조회() {
        // when
        var 취미_페이징_조회_요청_결과 = 취미_페이징_조회_요청(취미_페이징_조회_URL, 토큰);

        // then
        취미_페이징_조회_요청_검증(취미_페이징_조회_요청_결과);
    }

    @Test
    void 취미_수정() {
        // given
        var 취미_업데이트_요청서 = 취미_업데이트_요청_생성();

        // when
        var 취미_수정_요청_결과 = 취미_수정_요청(취미_업데이트_요청서, 취미_수정_URL, 토큰);

        // then
        취미_수정_요청_검증(취미_수정_요청_결과);
    }

    @Test
    void 취미_삭제() {
        // given
        새로운_취미_생성();

        // when
        var 취미_삭제_요청_결과 = 취미_삭제_요청(취미_삭제_URI, 토큰);

        // then
        취미_삭제_요청_검증(취미_삭제_요청_결과);
    }
}
