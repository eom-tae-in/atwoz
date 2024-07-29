package com.atwoz.alert.ui;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertControllerAcceptanceTest extends AlertControllerAcceptanceFixture {

    private static final String 알림_페이징_URI = "/api/members/me/alerts?page=0&size=3";
    private static final String 알림_단일_URI = "/api/members/me/alerts/";

    private String 토큰;
    private Member 회원;

    @BeforeEach
    void setup() {
        회원 = 회원_생성();
        토큰 = 토큰_생성(회원);
    }

    @Test
    void 알림을_페이징_조회한다() {
        // given
        알림_목록_생성(회원.getId());

        // when
        var 알림_목록_조회_결과 = 알림_목록을_조회한다(토큰, 알림_페이징_URI);

        // then
        알림_목록_조회_결과_검증(알림_목록_조회_결과);
    }

    @Test
    void 알림을_단일_조회한다() {
        // given
        var 알림 = 알림_생성(회원.getId());

        // when
        var 알림_조회_결과 = 알림을_조회한다(토큰, 알림_단일_URI + 알림.getId());

        // then
        알림_조회_검증(알림_조회_결과);
    }
}
