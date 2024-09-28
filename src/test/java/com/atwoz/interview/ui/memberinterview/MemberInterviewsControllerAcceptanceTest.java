package com.atwoz.interview.ui.memberinterview;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.memberinterview.MemberInterviewSubmitRequestFixture.회원_인터뷰_응시_요청;
import static com.atwoz.interview.fixture.memberinterview.MemberInterviewUpdateRequestFixture.회원_인터뷰_수정_요청;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewsControllerAcceptanceTest extends MemberInterviewsControllerAcceptanceFixture {

    private static final String 인터뷰_응시_URI = "/api/members/me/interviews";
    private static final String 인터뷰_수정_URI = "/api/members/me/interviews";
    private static final String 인터뷰_조회_URI = "/api/members/me/interviews";
    private static final String 인터뷰_타입_목록_조회_URI = "/api/members/me/interviews?type=나";

    private String 토큰;
    private Member 회원;

    @BeforeEach
    void setup() {
        회원 = 회원_생성();
        토큰 = 토큰_생성(회원);
    }

    @Test
    void 인터뷰를_응시한다() {
        // given
        인터뷰_생성();
        var 인터뷰_id = 1L;
        var 인터뷰_응시_요청서 = 회원_인터뷰_응시_요청();

        // when
        var 인터뷰_응시_결과 = 인터뷰_응시_요청(인터뷰_응시_URI, 토큰, 인터뷰_id, 인터뷰_응시_요청서);

        // then
        인터뷰_응시_검증(인터뷰_응시_결과);
    }

    @Test
    void 인터뷰_답변을_수정한다() {
        // given
        인터뷰_생성();
        인터뷰_응시(회원.getId());
        var 인터뷰_id = 1L;
        var 인터뷰_수정_요청서 = 회원_인터뷰_수정_요청();

        // when
        var 인터뷰_수정_결과 = 인터뷰_수정_요청(인터뷰_수정_URI, 토큰, 인터뷰_id, 인터뷰_수정_요청서);

        // then
        인터뷰_수정_검증(인터뷰_수정_결과);
    }

    @Test
    void 인터뷰_답변을_조회한다() {
        // given
        인터뷰_생성();
        인터뷰_응시(회원.getId());
        var 인터뷰_id = 1L;

        // when
        var 인터뷰_조회_결과 = 인터뷰_조회_요청(인터뷰_조회_URI, 인터뷰_id, 토큰);

        // then
        인터뷰_조회_검증(인터뷰_조회_결과);
    }

    @Test
    void 타입으로_인터뷰를_조회한다() {
        // given
        인터뷰_목록_생성();
        인터뷰_목록_응시(회원.getId());

        // when
        var 인터뷰_타입_조회_결과 = 인터뷰_목록_조회_요청(인터뷰_타입_목록_조회_URI, 토큰);

        // then
        인터뷰_타입_조회_검증(인터뷰_타입_조회_결과);
    }
}
