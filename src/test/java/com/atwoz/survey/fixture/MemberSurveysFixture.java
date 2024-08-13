package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;

import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_30개;
import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_30개_과목_문제_답안;
import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_두개;

@SuppressWarnings("NonAsciiCharacters")
public class MemberSurveysFixture {

    public static MemberSurveys 회원_연애고사_필수_과목_질문_두개_응시_저장() {
        Long memberId = 1L;
        return MemberSurveys.builder()
                .memberId(memberId)
                .memberSurveys(회원_연애고사_응시_필수_과목_두개())
                .build();
    }

    public static MemberSurveys 회원_연애고사_필수_과목_질문_30개_응시_저장_id(final Long memberId) {
        return MemberSurveys.builder()
                .memberId(memberId)
                .memberSurveys(회원_연애고사_응시_필수_과목_30개())
                .build();
    }

    public static MemberSurveys 회원_연애고사_필수_과목_질문_30개_응시_저장_id_과목_답안(
            final Long memberId,
            final Long surveyId,
            final Integer answer
    ) {
        return MemberSurveys.builder()
                .memberId(memberId)
                .memberSurveys(회원_연애고사_응시_필수_과목_30개_과목_문제_답안(surveyId, answer))
                .build();
    }
}
