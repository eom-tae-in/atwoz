package com.atwoz.mission.ui.membermission;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsControllerAcceptanceTest extends MemberMissionsControllerAcceptanceFixture {

    private static final String 회원_미션_페이징_url = "/api/members/me/missions?page=0&size=10";
    private static final String 회원_미션_클리어_조회_url = "/api/members/me/missions/clear?status=true";
    private static final String 회원_미션_등록_url = "/api/members/me/missions/";
    private static final String 회원_미션_클리어_url = "/api/members/me/missions/";
    private static final String 회원_완료_미션_보상_조회_url = "/api/members/me/missions/";
    private static final String 회원_완료_미션_보상_총합_조회_url = "/api/members/me/missions/reward";

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
        회원_완료_미션_등록();

        // when
        var 회원_미션_페이징_조회_결과 = 회원_미션을_페이징_조회한다(토큰, 회원_미션_페이징_url);

        // then
        회원_미션_페이징_조회_결과_검증(회원_미션_페이징_조회_결과);
    }

    @Test
    void 회원의_미션_목록을_클리어_여부로_페이징_조회한다() {
        // given
        회원_완료_미션_등록();

        // when
        var 회원_미션_status_조회_결과 = 회원_미션을_클리어_여부로_조회한다(토큰, 회원_미션_클리어_조회_url);

        // then
        회원_미션_status_조회_결과_검증(회원_미션_status_조회_결과);
    }

    @Test
    void 회원의_완료된_미션의_보상_총합을_조회한다() {
        // given
        회원_완료_미션_등록();

        // when
        var 회원_완료_미션_보상_총합_조회_결과 = 회원_미션_목록의_완료된_미션의_보상_총합을_조회한다(토큰, 회원_완료_미션_보상_총합_조회_url);

        // then
        회원의_완료된_미션_보상_총합_조회_결과_검증(회원_완료_미션_보상_총합_조회_결과);
    }

    @Test
    void 회원_미션_목록에_미션을_등록한다() {
        // given
        var 미션 = 미션_생성();

        // when
        var 회원_미션_등록_결과 = 회원_미션을_등록한다(토큰, 미션.getId(), 회원_미션_등록_url);

        // then
        회원_미션_등록_결과_검증(회원_미션_등록_결과);
    }

    @Test
    void 회원의_미션_목록에서_완료된_특정_미션의_보상을_조회한다() {
        // given
        회원_완료_미션_등록();
        var 미션_id = 1L;

        // when
        var 회원_완료_특정_미션_보상_조회_결과 = 회원의_완료된_특정_미션_보상을_조회한다(토큰, 미션_id, 회원_완료_미션_보상_조회_url);

        // then
        회원의_완료된_특정_미션_보상_조회_결과_검증(회원_완료_특정_미션_보상_조회_결과);
    }

    @Test
    void 회원의_미션을_클리어한다() {
        // given
        회원_미완료_미션_등록();
        var 미션_id = 1L;

        // when
        var 회원_미션_클리어_결과 = 회원의_미션을_클리어한다(토큰, 미션_id, 회원_미션_클리어_url);

        // then
        회원_미션_클리어_결과_검증(회원_미션_클리어_결과);
    }

    @Nested
    class 회원_미션_예외 {

        @Test
        void 회원의_미션_목록이_없으면_보상_총합_조회를_할_수_없다() {
            // when
            var 회원_완료_미션_보상_총합_조회_결과 = 회원_미션_목록의_완료된_미션의_보상_총합을_조회한다(토큰, 회원_완료_미션_보상_총합_조회_url);

            // then
            회원의_완료된_미션_보상_총합_조회_결과_예외_검증(회원_완료_미션_보상_총합_조회_결과);
        }

        @Test
        void 존재하지_않는_미션은_등록할_수_없다() {
            // given
            var 미션_id = -1L;

            // when
            var 회원_미션_등록_결과 = 회원_미션을_등록한다(토큰, 미션_id, 회원_미션_등록_url);

            // then
            회원_미션_등록_결과_예외_검증(회원_미션_등록_결과);
        }

        @Test
        void 아직_완료되지_않은_미션의_보상을_조회하면_예외가_발생한다() {
            // given
            회원_미완료_미션_등록();
            var 미션_id = 1L;

            // when
            var 회원_완료_특정_미션_보상_조회_결과 = 회원의_완료된_특정_미션_보상을_조회한다(토큰, 미션_id, 회원_완료_미션_보상_조회_url);

            // then
            회원의_완료된_특정_미션_보상_조회_미완료_예외_검증(회원_완료_특정_미션_보상_조회_결과);
        }

        @Test
        void 회원의_미션_목록에_없는_미션의_보상을_조회하면_예외가_발생한다() {
            // given
            var 미션 = 미션_생성();

            // when
            var 회원_완료_특정_미션_보상_조회_결과 = 회원의_완료된_특정_미션_보상을_조회한다(토큰, 미션.getId(), 회원_완료_미션_보상_조회_url);

            // then
            회원의_완료된_특정_미션_보상_조회_회원_미션_없음_예외_검증(회원_완료_특정_미션_보상_조회_결과);
        }

        @Test
        void 아예_없는_미션의_보상을_조회하면_예외가_발생한다() {
            // given
            var 미션_id = -1L;

            // when
            var 회원_완료_특정_미션_보상_조회_결과 = 회원의_완료된_특정_미션_보상을_조회한다(토큰, 미션_id, 회원_완료_미션_보상_조회_url);

            // then
            회원의_완료된_특정_미션_보상_조회_미션_없음_예외_검증(회원_완료_특정_미션_보상_조회_결과);
        }

        @Test
        void 회원_미션_목록에_없는_모든_미션은_클리어할_수_없다() {
            // given
            var 미션_id = 1L;

            // when
            var 회원_미션_클리어_결과 = 회원의_미션을_클리어한다(토큰, 미션_id, 회원_미션_클리어_url);

            // then
            회원_미션_클리어_결과_미션_없음_예외_검증(회원_미션_클리어_결과);
        }
    }
}
