package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;

import java.util.List;

public class MemberSurveyFixture {

    public static List<MemberSurvey> 회원_연애고사_응시_필수_과목_두개() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Long questionTwoId = 2L;
        Integer answerTwoNumber = 2;

        return List.of(
                MemberSurvey.of(surveyId, questionOneId, answerOneNumber),
                MemberSurvey.of(surveyId, questionTwoId, answerTwoNumber)
        );
    }
}
