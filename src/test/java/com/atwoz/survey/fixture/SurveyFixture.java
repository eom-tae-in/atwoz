package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.Survey;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.설문_필수_질문_과목_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyQuestionFixture.설문_질문_답변_한개_id있음;

public class SurveyFixture {

    public static Survey 설문_필수_질문_과목_두개씩() {
        return Survey.createWith(설문_필수_질문_과목_두개씩_생성_요청());
    }

    public static Survey 설문_필수_질문_과목_한개씩_전부_id_있음() {
        return Survey.builder()
                .id(1L)
                .required(true)
                .name("과목 1")
                .questions(List.of(설문_질문_답변_한개_id있음()))
                .build();
    }
}
