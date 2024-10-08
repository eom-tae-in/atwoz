//package com.atwoz.memberlike.ui;
//
//import com.atwoz.member.domain.member.Member;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayNameGeneration;
//import org.junit.jupiter.api.DisplayNameGenerator;
//import org.junit.jupiter.api.Test;
//
//import static com.atwoz.memberlike.fixture.MemberLikeCreateRequestFixture.호감_요청_생성;
//
//@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
//@SuppressWarnings("NonAsciiCharacters")
//class MemberLikeControllerAcceptanceTest extends MemberLikeControllerAcceptanceFixture {
//
//    private static final String 호감_URI = "/api/members/me/likes";
//    private static final String 보낸_호감_조회_URI = "/api/members/me/likes/sent?page=0&size=2";
//    private static final String 받은_호감_조회_URI = "/api/members/me/likes/received?page=0&size=2";
//
//    private String 토큰;
//    private Member 회원;
//
//    @BeforeEach
//    void setup() {
//        회원 = 회원_생성();
//        토큰 = 토큰_생성(회원);
//    }
//
//    @Test
//    void 호감을_보낸다() {
//        // given
//        var 상대 = 회원_생성();
//        var 호감_전송_요청 = 호감_요청_생성(상대.getId());
//
//        // when
//        var 호감_전송_결과 = 호감을_보낸다(토큰, 호감_URI, 호감_전송_요청);
//
//        // then
//        호감_전송_결과_검증(호감_전송_결과);
//    }
//
//    @Test
//    void 보낸_호감을_조회한다() {
//        // given
//        보낸_호감_목록_생성(회원.getId());
//
//        // when
//        var 보낸_호감_조회_결과 = 보낸_호감을_조회한다(토큰, 보낸_호감_조회_URI);
//
//        // then
//        보낸_호감_조회_결과_검증(보낸_호감_조회_결과);
//    }
//
//    @Test
//    void 받은_호감을_조회한다() {
//        // given
//        받은_호감_목록_생성(회원.getId());
//
//        // when
//        var 받은_호감_조회_결과 = 받은_호감을_조회한다(토큰, 받은_호감_조회_URI);
//
//        // then
//        받은_호감_조회_결과_검증(받은_호감_조회_결과);
//    }
//}
