package com.atwoz.survey.fixture;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;
import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyResponse;

import java.util.List;

public class MemberSurveyResponseFixture {

    public static MemberSurveyResponse 회원_연애고사_응시_조회() {
        Long memberId = 1L;

        return new MemberSurveyResponse(memberId, List.of(
                new MemberSurveyQuestionResponse(1L, 1),
                new MemberSurveyQuestionResponse(2L, 2)
        ));
    }
}
