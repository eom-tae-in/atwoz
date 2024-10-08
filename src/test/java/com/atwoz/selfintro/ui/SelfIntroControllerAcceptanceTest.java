//package com.atwoz.selfintro.ui;
//
//import com.atwoz.member.domain.member.Member;
//import com.atwoz.selfintro.domain.SelfIntro;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
//import org.junit.jupiter.api.Test;
//
//import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_생성_요청_픽스처.셀프소개글_생성_요청서;
//import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_수정_요청_픽스처.셀프소개글_수정_요청서;
//import static com.atwoz.selfintro.fixture.셀프소개글_픽스처.셀프소개글_생성;
//
//@DisplayNameGeneration(ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class SelfIntroControllerAcceptanceTest extends SelfIntroControllerAcceptanceTestFixture {
//
//    private static final String 셀프소개글_생성_URL = "/api/members/self-intros/me";
//    private static final String 필터_적용한_셀프소개글_조회_URL = "/api/members/self-intros/filter";
//    private static final String 셀프소개글_수정_URL = "/api/members/self-intros/1";
//    private static final String 셀프소개글_삭제_URI = "/api/members/self-intros/1";
//
//    private String 토큰;
//    private SelfIntro 셀프소개글;
//
//    @BeforeEach
//    void setup() {
//        셀프소개글 = 셀프소개글_생성();
//        Member 회원 = 회원_생성();
//        토큰 = 토큰_생성(회원);
//    }
//
//    @Test
//    void 셀프소개글_저장() {
//        // given
//        var 생성_요청서 = 셀프소개글_생성_요청서();
//
//        // when
//        var 셀프소개글_저장_요청_결과 = 셀프소개글_저장_요청(생성_요청서, 셀프소개글_생성_URL, 토큰);
//
//        // then
//        셀프소개글_저장_검증(셀프소개글_저장_요청_결과);
//    }
//
//    @Test
//    void 필터_적용한_셀프소개글_페이징_조회() {
//        // given
//        셀프소개글_저장(셀프소개글);
//
//        // when
//        var 필터_적용한_셀프소개글_페이징_조회_요청_결과 = 필터_적용한_셀프소개글_페이징_조회_요청(필터_적용한_셀프소개글_조회_URL, 토큰);
//
//        // then
//        필터_적용한_셀프소개글_페이징_조회_요청_검증(필터_적용한_셀프소개글_페이징_조회_요청_결과);
//    }
//
//    @Test
//    void 셀프소개글_수정() {
//        // given
//        셀프소개글_저장(셀프소개글);
//        var 수정_요청서 = 셀프소개글_수정_요청서();
//
//        // when
//        var 셀프소개글_수정_요청_결과 = 셀프소개글_수정_요청(수정_요청서, 셀프소개글_수정_URL, 토큰);
//
//        // then
//        셀프소개글_수정_요청_검증(셀프소개글_수정_요청_결과);
//    }
//
//    @Test
//    void 셀프소개글_삭제() {
//        // given
//        셀프소개글_저장(셀프소개글);
//
//        // when
//        var 셀프소개글_삭제_요청_결과 = 셀프소개글_삭제_요청(셀프소개글_삭제_URI, 토큰);
//
//        // then
//        셀프소개글_삭제_요청_검증(셀프소개글_삭제_요청_결과);
//    }
//}
