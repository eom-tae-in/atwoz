package com.atwoz.member.ui.member.profile.style;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.dto.request.StyleCreateRequestFixture.스타일_생성_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.StyleUpdateRequestFixture.스타일_업데이트_요청_생성;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleControllerAcceptanceTest extends StyleControllerAcceptanceTestFixture {

    private static final String 스타일_저장_URL = "/api/members/styles";
    private static final String 스타일_단건_조회_URL = "/api/members/styles/1";
    private static final String 스타일_페이징_조회_URL = "/api/members/styles";
    private static final String 스타일_수정_URL = "/api/members/styles/1";
    private static final String 스타일_삭제_URL = "/api/members/styles/2";

    private String 토큰;

    @BeforeEach
    void setup() {
        토큰 = 토큰_생성(회원_생성());
    }

    @Test
    void 스타일_저장() {
        // given
        var 스타일_생성_요청서 = 스타일_생성_요청_생성();

        // when
        var 스타일_저장_요청_결과 = 스타일_저장_요청(스타일_생성_요청서, 스타일_저장_URL, 토큰);

        // then
        스타일_저장_요청_검증(스타일_저장_요청_결과);
    }

    @Test
    void 스타일_단건_조회() {
        // when
        var 스타일_단건_조회_요청_결과 = 스타일_단건_조회_요청(스타일_단건_조회_URL, 토큰);

        // then
        스타일_단건_조회_요청_검증(스타일_단건_조회_요청_결과);
    }

    @Test
    void 스타일_페이징_조회() {
        // when
        var 스타일_페이징_조회_요청_결과 = 스타일_페이징_조회_요청(스타일_페이징_조회_URL, 토큰);

        // then
        스타일_페이징_조회_요청_검증(스타일_페이징_조회_요청_결과);
    }

    @Test
    void 스타일_수정() {
        // given
        var 스타일_업데이트_요청서 = 스타일_업데이트_요청_생성();

        // when
        var 스타일_수정_요청_결과 = 스타일_수정_요청(스타일_업데이트_요청서, 스타일_수정_URL, 토큰);

        // then
        스타일_수정_요청_검증(스타일_수정_요청_결과);
    }

    @Test
    void 스타일_삭제() {
        // given
        새로운_스타일_생성();

        // when
        var 스타일_삭제_요청_결과 = 스타일_삭제_요청(스타일_삭제_URL, 토큰);

        // then
        스타일_삭제_요청_검증(스타일_삭제_요청_결과);
    }
}
