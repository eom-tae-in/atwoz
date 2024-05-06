package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.SurveyAnswer;

public class SurveyAnswerFixture {

    public static SurveyAnswer 설문_답변_id있음() {
        return SurveyAnswer.builder()
                .id(1L)
                .description("답변 1")
                .build();
    }
}
