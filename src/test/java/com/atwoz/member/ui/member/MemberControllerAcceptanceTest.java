package com.atwoz.member.ui.member;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberControllerAcceptanceTest extends MemberControllerAcceptanceFixture {

    private static final String 회원_닉네임_중복_검증_URL = "/api/members/{nickname}/existence";
    private static final String 회원_정보_변경_URL = "/api/members/me";
    private static final String 회원_정보_조회_URL = "/api/members/{memberId}";
    private static final String 오늘의_이성_추천_프로필_조회_URL = "/api/members/profiles/today";
    private static final String 인기있는_이성_회원_프로필_조회_URL = "/api/members/profiles/popularity";
    private static final String 오늘_방문한_이성_회원_프로필_조회_URL = "/api/members/profiles/today-visit";
    private static final String 근처에_있는_이성_회원_프로필_조회_URL = "/api/members/profiles/nearby";
    private static final String 최근에_가입한_이성_회원_프로필_조회_URL = "/api/members/profiles/recency";
    private static final String 종교가_같은_이성_회원_프로필_조회_URL = "/api/members/profiles/religion";
    private static final String 취미가_같은_이성_회원_프로필_조회_URL = "/api/members/profiles/hobbies";

    private String 토큰;
    private Member 회원;

    @BeforeEach
    void setup() {
        회원 = 일반_회원_생성();
        토큰 = 토큰_생성(회원);
    }

    @Test
    void 닉네임_중복을_확인한다() {
        // given
        var 회원_닉네임 = 회원_닉네임을_요청한다();

        // when
        var 회원_닉네임_중복_확인_결과 = 회원_닉네임_중복을_확인한다(회원_닉네임_중복_검증_URL, 토큰, 회원_닉네임);

        // then
        회원_닉네임_중복_확인_검증(회원_닉네임_중복_확인_결과);
    }

    @Test
    void 회원_정보를_초기화한다() {
        // given
        var 회원_초기_정보 = 회원_초기_정보를_요청한다();

        // when
        var 회원_정보_초기화_요청_결과 = 회원_정보_초기화_요청(회원_정보_변경_URL, 토큰, 회원_초기_정보);

        // then
        회원_정보_초기화_검증(회원_정보_초기화_요청_결과);
    }

    @Test
    void 회원_정보를_조회한다() {
        // when
        var 회원_조회_요청_결과 = 회원_조회_요청(회원_정보_조회_URL, 토큰, 회원);

        // then
        회원_정보_조회_검증(회원_조회_요청_결과, 회원);
    }

    @Test
    void 오늘의_이성_추천_프로필을_조회한다() {
        // given
        var 다이아_등급_회원 = 다이아_등급_이성_회원_생성();
        var 골드_등급_회원 = 골드_등급_이성_회원_생성();

        // when
        var 오늘의_이성_추천_프로필_조회_요청_결과 = 프로필_조회_요청(오늘의_이성_추천_프로필_조회_URL, 토큰);

        // then
        오늘의_이성_추천_프로필_조회_요청_검증(오늘의_이성_추천_프로필_조회_요청_결과, 골드_등급_회원, 다이아_등급_회원);
    }

    @Test
    void 인기있는_이성_회원_프로필을_조회한다() {
        // given
        var 인기있는_회원 = 인기있는_회원_생성(회원);

        // when
        var 인기있는_이성_회원_프로필_조회_요청_결과 = 프로필_조회_요청(인기있는_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(인기있는_이성_회원_프로필_조회_요청_결과, 인기있는_회원);
    }

    @Test
    void 오늘_방문한_이성_회원_프로필을_조회한다() {
        // given
        var 오늘_방문한_회원 = 오늘_방문한_이성_회원_생성();

        // when
        var 오늘_방문한_이성_회원_프로필_조회_요청_결과 = 프로필_조회_요청(오늘_방문한_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(오늘_방문한_이성_회원_프로필_조회_요청_결과, 오늘_방문한_회원);
    }

    @Test
    void 근처에_있는_이성_회원_프로필을_조회한다() {
        // given
        var 근처에_있는_회원 = 근처에_있는_이성_회원_생성(회원);

        // when
        var 근처에_있는_이성_회원_프로필_조회_결과 = 프로필_조회_요청(근처에_있는_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(근처에_있는_이성_회원_프로필_조회_결과, 근처에_있는_회원);
    }

    @Test
    void 최근에_가입한_이성_회원_프로필을_조회한다() {
        // given
        var 최근에_가입한_회원 = 최근에_가입한_이성_회원_생성();

        // when
        var 최근에_가입한_이성_회원_프로필_조회_결과 = 프로필_조회_요청(최근에_가입한_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(최근에_가입한_이성_회원_프로필_조회_결과, 최근에_가입한_회원);
    }

    @Test
    void 종교가_같은_이성_회원_프로필을_조회한다() {
        // given
        var 종교가_같은_회원 = 종교가_같은_이성_회원_생성(회원);

        // when
        var 종교가_같은_이성_회원_프로필_조회_결과 = 프로필_조회_요청(종교가_같은_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(종교가_같은_이성_회원_프로필_조회_결과, 종교가_같은_회원);
    }

    @Test
    void 취미가_같은_이성_회원_프로필을_조회한다() {
        // given
        var 취미가_같은_회원 = 취미가_같은_이성_회원_생성(회원);

        // when
        var 취미가_같은_이성_회원_프로필_조회_결과 = 프로필_조회_요청(취미가_같은_이성_회원_프로필_조회_URL, 토큰);

        // then
        이성_회원_프로필_조회_요청_검증(취미가_같은_이성_회원_프로필_조회_결과, 취미가_같은_회원);
    }

    @Test
    void 회원_정보를_업데이트한다() {
        // given
        var 회원_업데이트_요청서 = 회원_업데이트_요청서_요청();

        // when
        var 회원_정보_수정_요청_결과 = 회원_정보_수정_요청(회원_정보_변경_URL, 토큰, 회원_업데이트_요청서);

        // then
        회원_정보_수정_검증(회원_정보_수정_요청_결과);
    }

    @Test
    void 회원탈퇴를_한다() {
        // when
        var 회원_탈퇴_요청_결과 = 회원_탈퇴_요청(회원_정보_변경_URL, 토큰);

        // then
        회원_탈퇴_요청_검증(회원_탈퇴_요청_결과);
    }
}
