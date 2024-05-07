package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.SurveyAnswer;

public class SurveyAnswerFixture {

    public static SurveyAnswer 연애고사_답변_id있음() {
        return SurveyAnswer.builder()
                .id(1L)
                .number(1)
                .description("답변 1")
                .build();
    }
}
