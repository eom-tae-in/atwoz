package com.atwoz.mission.ui.membermission;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsControllerAcceptanceTest extends MemberMissionsControllerAcceptanceFixture {

    private static final String 회원_미션_페이징_url = "/api/members/me/missions?page=0&size=10";
    private static final String 회원_미션_클리어_조회_url = "/api/members/me/missions/clear?status=true";

    private Member 회원;
    private String 토큰;

    @BeforeEach
    void setup() {
        회원 = 회원_생성();
        토큰 = 토큰_생성(회원.getEmail());
    }

    @Test
    void 회원의_미션_목록을_페이징_조회한다() {
        // given
        회원_완료_미션_등록_보상_미수령();

        // when
        var 회원_미션_페이징_조회_결과 = 회원_미션을_페이징_조회한다(토큰, 회원_미션_페이징_url);

        // then
        회원_미션_페이징_조회_결과_검증(회원_미션_페이징_조회_결과);
    }

    @Test
    void 회원의_미션_목록을_클리어_여부로_페이징_조회한다() {
        // given
        회원_완료_미션_등록_보상_미수령();

        // when
        var 회원_미션_status_조회_결과 = 회원_미션을_클리어_여부로_조회한다(토큰, 회원_미션_클리어_조회_url);

        // then
        회원_미션_status_조회_결과_검증(회원_미션_status_조회_결과);
    }
}
