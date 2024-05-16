package com.atwoz.survey.fixture;

import com.atwoz.survey.infrastructure.membersurvey.dto.MemberSurveyQuestionResponse;

import java.util.List;

public class MemberSurveyQuestionResponseFixture {

    public static List<MemberSurveyQuestionResponse> 회원_연애고사_과목_조회_응답() {
        Long questionOneId = 1L;
        Integer answerOne = 1;
        Long questionTwoId = 2L;
        Integer answerTwo = 2;

        return List.of(
                new MemberSurveyQuestionResponse(questionOneId, answerOne),
                new MemberSurveyQuestionResponse(questionTwoId, answerTwo)
        );
    }
}
