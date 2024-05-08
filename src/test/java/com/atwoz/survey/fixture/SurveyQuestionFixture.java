package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.SurveyQuestion;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyAnswerFixture.연애고사_답변_id있음;
import static com.atwoz.survey.fixture.SurveyAnswerFixture.연애고사_답변_두개_id_있음_두번째;
import static com.atwoz.survey.fixture.SurveyAnswerFixture.연애고사_답변_두개_id_있음_첫번째;

public class SurveyQuestionFixture {

    public static SurveyQuestion 연애고사_질문_답변_한개_id_있음() {
        return SurveyQuestion.builder()
                .id(1L)
                .description("질문 1")
                .answers(List.of(연애고사_답변_id있음()))
                .build();
    }

    public static List<SurveyQuestion> 연애고사_질문_답변_두개_id_있음() {
        return List.of(
                SurveyQuestion.builder()
                        .id(1L)
                        .description("질문 내용1")
                        .answers(연애고사_답변_두개_id_있음_첫번째())
                        .build(),
                SurveyQuestion.builder()
                        .id(2L)
                        .description("질문 내용2")
                        .answers(연애고사_답변_두개_id_있음_두번째())
                        .build()
        );
    }
}
