package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.SurveyQuestion;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyAnswerFixture.연애고사_답변_id있음;

public class SurveyQuestionFixture {

    public static SurveyQuestion 연애고사_질문_답변_한개_id있음() {
        return SurveyQuestion.builder()
                .id(1L)
                .description("질문 1")
                .answers(List.of(연애고사_답변_id있음()))
                .build();
    }
}
