package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.SurveyAnswer;

import java.util.List;

public class SurveyAnswerFixture {

    public static SurveyAnswer 연애고사_답변_id있음() {
        return SurveyAnswer.builder()
                .id(1L)
                .number(1)
                .description("답변 1")
                .build();
    }

    public static List<SurveyAnswer> 연애고사_답변_두개_id_있음_첫번째() {
        return List.of(
                SurveyAnswer.builder()
                        .id(1L)
                        .number(1)
                        .description("답변 1")
                        .build(),
                SurveyAnswer.builder()
                        .id(2L)
                        .number(2)
                        .description("답변 2")
                        .build()
        );
    }

    public static List<SurveyAnswer> 연애고사_답변_두개_id_있음_두번째() {
        return List.of(
                SurveyAnswer.builder()
                        .id(3L)
                        .number(1)
                        .description("답변 1")
                        .build(),
                SurveyAnswer.builder()
                        .id(4L)
                        .number(2)
                        .description("답변 2")
                        .build()
        );
    }
}
