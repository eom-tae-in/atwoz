package com.atwoz.member.ui.member;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_계정_상태_변경_요청_픽스처.회원_계정_상태_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_연락처_정보_변경_요청_픽스처.회원_연락처_정보_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_푸시_알림_변경_요청_픽스처.회원_푸시_알림_변경_요청_생성;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberControllerAcceptanceTest extends MemberControllerAcceptanceFixture {

    @Test
    void 회원의_푸시_알림_정보를_조회한다() {
        // when
        var 회원_푸시_알림_정보_조회_결과 = 회원_푸시_알림_정보_조회_요청(회원_푸시_알림_조회_URL, 토큰);

        // then
        회원_푸시_알림_정보_조회_검증(회원_푸시_알림_정보_조회_결과);
    }

    @Test
    void 회원의_계정_상태를_조회한다() {
        // when
        var 회원_계정_상태_정보_조회_결과 = 회원_계정_상태_정보_조회_요청(회원_계정_상태_조회_URL, 토큰);

        // then
        회원_계정_상태_정보_조회_검증(회원_계정_상태_정보_조회_결과);
    }

    @Test
    void 회원의_연락처_정보를_조회한다() {
        // when
        var 회원_연락처_정보_조회_결과 = 회원_연락처_정보_조회_요청(회원_연락처_정보_조회_URL, 토큰);

        // then
        회원_연락처_정보_조회_검증(회원_연락처_정보_조회_결과);
    }

    @Test
    void 연락처_정보가_중복되는지_확인한다() {
        // given
        var 연락처_타입 = 중복_확인을_할_연락처_타입_생성();
        var 연락처_정보 = 중복_확인을_할_연락처_정보_생성();

        // when
        var 회원_연락처_정보_중복_확인_결과 = 회원_연락처_정보_중복_확인_요청(회원_연락처_정보_중복_확인_URL, 토큰, 연락처_타입, 연락처_정보);

        // then
        회원_연락처_정보_중복_확인_검증(회원_연락처_정보_중복_확인_결과);
    }

    @Test
    void 회원의_푸시_알림_설정을_변경한다() {
        // given
        var 회원_푸시_알림_변경_요청서 = 회원_푸시_알림_변경_요청_생성();

        // when
        var 회원_푸시_알림_변경_결과 = 회원_푸시_알림_변경_요청(회원_푸시_알림_변경_URL, 토큰, 회원_푸시_알림_변경_요청서);

        // then
        회원_푸시_알림_변경_검증(회원_푸시_알림_변경_결과);
    }

    @Test
    void 회원의_계정_상태를_변경한다() {
        // given
        var 회원_계정_상태_변경_요청서 = 회원_계정_상태_변경_요청_생성();

        // when
        var 회원_계정_상태_변경_결과 = 회원_계정_상태_변경_요청(회원_계정_상태_변경_URL, 토큰, 회원_계정_상태_변경_요청서);

        // then
        회원_계정_상태_변경_검증(회원_계정_상태_변경_결과);
    }

    @Test
    void 회원의_연락처_정보를_변경한다() {
        // given
        var 회원_연락처_정보_변경_요청서 = 회원_연락처_정보_변경_요청_생성();

        // when
        var 회원_연락처_정보_변경_결과 = 회원_연락처_정보_변경_요청(회원_연락처_정보_변경_URL, 토큰, 회원_연락처_정보_변경_요청서);

        // then
        회원_연락처_정보_변경_검증(회원_연락처_정보_변경_결과);
    }

    @Test
    void 회원탈퇴를_한다() {
        // when
        var 회원_탈퇴_요청_결과 = 회원_탈퇴_요청(회원_삭제_URL, 토큰);

        // then
        회원_탈퇴_요청_검증(회원_탈퇴_요청_결과);
    }
}
