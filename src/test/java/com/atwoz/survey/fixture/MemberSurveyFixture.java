package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.membersurvey.MemberSurvey;

import java.util.ArrayList;
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

    public static List<MemberSurvey> 회원_연애고사_응시_필수_과목_30개() {
        List<MemberSurvey> memberSurveys = new ArrayList<>();
        for (long id = 1; id <= 30; id++) {
            memberSurveys.add(MemberSurvey.of(id, id, 1));
        }
        return memberSurveys;
    }

    public static List<MemberSurvey> 회원_연애고사_응시_필수_과목_30개_과목_문제_답안(final Long surveyId, final Integer number) {
        List<MemberSurvey> memberSurveys = new ArrayList<>();
        for (long id = 1; id <= 30; id++) {
            memberSurveys.add(MemberSurvey.of(surveyId, id, number));
        }
        return memberSurveys;
    }
}
