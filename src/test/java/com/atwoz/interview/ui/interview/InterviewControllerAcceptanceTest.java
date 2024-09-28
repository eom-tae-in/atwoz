package com.atwoz.interview.ui.interview;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.interview.InterviewCreateRequestFixture.인터뷰_나_질문_요청;
import static com.atwoz.interview.fixture.interview.InterviewUpdateRequestFixture.인터뷰_수정_질문_요청;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewControllerAcceptanceTest extends InterviewControllerAcceptanceFixture {

    private static final String 인터뷰_등록_URI = "/api/interviews";
    private static final String 인터뷰_수정_URI = "/api/interviews";
    private static final String 인터뷰_타입_목록_조회_URI = "/api/interviews?type=나";

    private String 토큰;
    private Member 관리자;

    @BeforeEach
    void setup() {
        관리자 = 회원_생성();
        토큰 = 토큰_생성(관리자);
    }

    @Test
    void 인터뷰를_등록한다() {
        // when
        var 인터뷰_생성_결과 = 인터뷰_생성_요청(인터뷰_등록_URI, 토큰, 인터뷰_나_질문_요청());

        // then
        인터뷰_생성_검증(인터뷰_생성_결과);
    }

    @Test
    void 인터뷰를_수정한다() {
        // given
        인터뷰_생성();
        var 인터뷰_id = 1L;

        // when
        var 인터뷰_수정_결과 = 인터뷰_수정_요청(인터뷰_수정_URI, 토큰, 인터뷰_수정_질문_요청(), 인터뷰_id);

        // then
        인터뷰_수정_검증(인터뷰_수정_결과);
    }

    @Test
    void 인터뷰를_타입으로_조회한다() {
        // given
        인터뷰_목록_생성();

        // when
        var 인터뷰_타입_조회_결과 = 인터뷰_조회_요청(인터뷰_타입_목록_조회_URI, 토큰);

        // then
        인터뷰_타입_조회_검증(인터뷰_타입_조회_결과);
    }
}
