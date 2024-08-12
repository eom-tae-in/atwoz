package com.atwoz.survey.ui.membersurvey;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.profile.physical.vo.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_30개_제출_요청_전부_1번;
import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_30개_제출_요청_전부_2번;
import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
public class MemberSurveysControllerAcceptanceTest extends MemberSurveysControllerAcceptanceFixture {

    private static final String 연애고사_응시_url = "/api/members/me/surveys";
    private static final String 연애고사_응시_조회_url = "/api/members/me/surveys";
    private static final String 연애고사_매칭_url = "/api/members/me/surveys/match";

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

    @Test
    void 응시한_연애고사를_과목별로_조회한다() {
        // given
        var 연애고사_과목_id = 1L;
        연애고사_필수_과목_질문_두개씩_생성();
        연애고사_응시_요청(연애고사_응시_url, 토큰, 필수_과목_질문_두개_제출_요청());

        // when
        var 연애고사_조회_결과 = 연애고사_응시_조회(연애고사_응시_조회_url, 토큰, 연애고사_과목_id);

        // then
        연애고사_조회_검증(연애고사_조회_결과);
    }

    @Test
    void 서로_응시_결과가_같고_성별이_다른_회원을_매칭한다() {
        // given
        연애고사_필수_과목_질문_30개씩_생성();
        연애고사_응시_요청(연애고사_응시_url, 토큰, 필수_과목_질문_30개_제출_요청_전부_1번());

        Member 다른_회원_1 = 회원_생성("남성1", "010-0000-0001", Gender.MALE);
        String 회원_1_토큰 = 토큰_생성(다른_회원_1);
        연애고사_응시_요청(연애고사_응시_url, 회원_1_토큰, 필수_과목_질문_30개_제출_요청_전부_1번());

        Member 다른_회원_2 = 회원_생성("여성1", "010-0000-0002", Gender.FEMALE);
        String 회원_2_토큰 = 토큰_생성(다른_회원_2);
        연애고사_응시_요청(연애고사_응시_url, 회원_2_토큰, 필수_과목_질문_30개_제출_요청_전부_2번());

        Member 다른_회원_3 = 회원_생성("여성2", "010-0000-0003", Gender.FEMALE);
        String 회원_3_토큰 = 토큰_생성(다른_회원_3);
        연애고사_응시_요청(연애고사_응시_url, 회원_3_토큰, 필수_과목_질문_30개_제출_요청_전부_1번());

        // when
        var 연애고사_매칭_결과 = 연애고사_매칭(연애고사_매칭_url, 토큰);

        // then
        연애고사_매칭_검증(연애고사_매칭_결과, 다른_회원_1.getId(), 다른_회원_2.getId(), 다른_회원_3.getId());
    }
}
