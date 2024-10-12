package com.atwoz.job.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.job.fixture.직업_요청_픽스처.직업_생성_요청_픽스처.직업_생성_요청_생성;
import static com.atwoz.job.fixture.직업_요청_픽스처.직업_수정_요청_픽스처.직업_업데이트_요청_생성;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class JobControllerAcceptanceTest extends JobControllerAcceptanceTestFixture {

    private static final String 직업_저장_URL = "/api/jobs";
    private static final String 직업_단건_조회_URL = "/api/jobs/1";
    private static final String 직업_페이징_조회_URL = "/api/jobs";
    private static final String 직업_수정_URL = "/api/jobs/1";
    private static final String 직업_삭제_URL = "/api/jobs/1";

    private String 토큰;

    @BeforeEach
    void setup() {
        토큰 = 토큰_생성(회원_생성());
    }

    @Test
    void 직업_저장() {
        // given
        var 직업_생성_요청서 = 직업_생성_요청_생성();

        // when
        var 직업_저장_요청_결과 = 직업_저장_요청(직업_생성_요청서, 직업_저장_URL, 토큰);

        // then
        직업_저장_요청_검증(직업_저장_요청_결과);
    }

    @Test
    void 직업_단건_조회() {
        // given
        직업_생성();

        // when
        var 직업_단건_조회_요청_결과 = 직업_단건_조회_요청(직업_단건_조회_URL, 토큰);

        // then
        직업_단건_조회_요청_검증(직업_단건_조회_요청_결과);
    }

    @Test
    void 직업_페이징_조회() {
        // given
        직업_생성();

        // when
        var 직업_페이징_조회_요청_결과 = 직업_페이징_조회_요청(직업_페이징_조회_URL, 토큰);

        // then
        직업_페이징_조회_요청_검증(직업_페이징_조회_요청_결과);
    }

    @Test
    void 직업_수정() {
        // given
        var 직업_업데이트_요청서 = 직업_업데이트_요청_생성();
        직업_생성();

        // when
        var 직업_수정_요청_결과 = 직업_수정_요청(직업_업데이트_요청서, 직업_수정_URL, 토큰);

        // then
        직업_수정_요청_검증(직업_수정_요청_결과);
    }

    @Test
    void 직업_삭제() {
        // given
        직업_생성();

        // when
        var 직업_삭제_요청_결과 = 직업_삭제_요청(직업_삭제_URL, 토큰);

        // then
        직업_삭제_요청_검증(직업_삭제_요청_결과);
    }
}
