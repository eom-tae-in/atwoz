package com.atwoz.member.ui.member;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberControllerAcceptanceTest extends MemberControllerAcceptanceFixture {

    private static final String 회원_URI = "/api/members/{nickname}/existence";
    private static final String 회원_정보_변경_URI = "/api/members/me";
    private static final String 회원_정보_조회_URI = "/api/members/{memberId}";

    private String 토큰;
    private Member 회원;

    @BeforeEach
    void setup() {
        회원 = 회원_생성();
        토큰 = 토큰_생성(회원);
    }

    @Test
    void 닉네임_중복을_확인한다() {
        // given
        var 회원_닉네임 = 회원_닉네임을_요청한다();

        // when
        var 회원_닉네임_중복_확인_결과 = 회원_닉네임_중복을_확인한다(회원_URI, 토큰, 회원_닉네임);

        // then
        회원_닉네임_중복_확인_검증(회원_닉네임_중복_확인_결과);
    }

    @Test
    void 회원_정보를_초기화한다() {
        // given
        var 회원_초기_정보 = 회원_초기_정보를_요청한다("newNickname");

        // when
        var 회원_정보_초기화_요청_결과 = 회원_정보_초기화_요청(회원_정보_변경_URI, 토큰, 회원_초기_정보);

        // then
        회원_정보_초기화_검증(회원_정보_초기화_요청_결과);
    }

    @Test
    void 회원_정보를_조회한다() {
        // when
        var 회원_조회_요청_결과 = 회원_조회_요청(회원_정보_조회_URI, 토큰, 회원);

        // then
        회원_정보_조회_검증(회원_조회_요청_결과);
    }

    @Test
    void 회원_정보를_수정한다() {
        // given
        var 회원_수정_정보 = 회원_수정_정보를_요청한다();

        // when
        var 회원_정보_수정_요청_결과 = 회원_정보_수정_요청(회원_정보_변경_URI, 토큰, 회원_수정_정보);

        // then
        회원_정보_수정_검증(회원_정보_수정_요청_결과);
    }

    @Test
    void 회원탈퇴를_한다() {
        // when
        var 회원_탈퇴_요청_결과 = 회원_탈퇴_요청(회원_정보_변경_URI, 토큰);

        // then
        회원_탈퇴_요청_검증(회원_탈퇴_요청_결과);
    }
}
