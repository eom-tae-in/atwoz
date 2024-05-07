package com.atwoz.survey.ui.survey;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_두개씩_생성_요청;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SurveyControllerAcceptanceTest extends SurveyControllerAcceptanceFixture {

    private static final String 연애고사_url = "/api/surveys";

    private Member 관리자;
    private String 토큰;

    @BeforeEach
    void setup() {
        관리자 = 회원_생성();
        토큰 = 토큰_생성(관리자);
    }

    @Test
    void 연애고사_과목을_생성한다() {
        // when
        var 연애고사_과목_생성_결과 = 연애고사_과목_생성_요청(연애고사_url, 토큰, 연애고사_필수_과목_질문_두개씩_생성_요청());

        // then
        연애고사_과목_생성_검증(연애고사_과목_생성_결과);
    }
}
