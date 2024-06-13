package com.atwoz.report.ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReportControllerAcceptanceTest extends ReportControllerAcceptanceFixture {

    private static final String 신고_URI = "/api/reports";

    private String 토큰;

    @BeforeEach
    void setup() {
        토큰 = 토큰_생성(회원_생성());
    }

    @Test
    void 불건전한_유저를_신고한다() {
        // given
        var 신고_요청서 = 신고_요청서_요청();

        // when
        var 신고_생성_요청_결과 = 신고_생성_요청(신고_URI, 토큰, 신고_요청서);

        // then
        신고_생성_요청_검증(신고_생성_요청_결과);
    }
}
