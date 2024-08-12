package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.membersurvey.MemberSurveys;

import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_30개;
import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_30개_번호;
import static com.atwoz.survey.fixture.MemberSurveyFixture.회원_연애고사_응시_필수_과목_두개;

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

    public static MemberSurveys 회원_연애고사_필수_과목_질문_30개_응시_저장_id_번호(final Long memberId, final Integer answer) {
        return MemberSurveys.builder()
                .memberId(memberId)
                .memberSurveys(회원_연애고사_응시_필수_과목_30개_번호(answer))
                .build();
    }
}
