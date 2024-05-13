package com.atwoz.survey.ui.membersurvey;

import com.atwoz.member.domain.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청;

public class MemberSurveysControllerAcceptanceTest extends MemberSurveysControllerAcceptanceFixture {

    private static final String 연애고사_응시_url = "/api/members/me/surveys/submit";

    private Member 회원;
    private String 토큰;

    @BeforeEach
    void setup() {
        회원 = 회원_생성();
        토큰 = 토큰_생성(회원);
    }

    @Test
    void 연애고사를_응시한다() {
        // given
        연애고사_필수_과목_질문_두개씩_생성();

        // when
        var 연애고사_응시_결과 = 연애고사_응시_요청(연애고사_응시_url, 토큰, 필수_과목_질문_두개_제출_요청());

        // then
        연애고사_응시_검증(연애고사_응시_결과);
    }
}
