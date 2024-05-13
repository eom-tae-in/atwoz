package com.atwoz.survey.domain.membersurvey;

import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_두개;
import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberSurveysTest {

    @Test
    void 회원_id로_기본_생성() {
        // given
        Long memberId = 1L;

        // when
        MemberSurveys memberSurveys = MemberSurveys.createWithMemberId(memberId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(memberSurveys.getMemberId()).isEqualTo(memberId);
            softly.assertThat(memberSurveys.getMemberSurveys()).isEmpty();
        });
    }

    @Test
    void 회원_연애고사_응시() {
        // given
        Long memberId = 1L;
        List<SurveySubmitRequest> requests = 필수_과목_질문_두개_제출_요청();
        MemberSurveys memberSurveys = MemberSurveys.createWithMemberId(memberId);
        List<MemberSurvey> expectMemberSurveys = 회원_연애고사_응시_필수_과목_두개();

        // when
        memberSurveys.submitSurveys(requests);

        // then
        assertThat(memberSurveys.getMemberSurveys()).isEqualTo(expectMemberSurveys);
    }
}
