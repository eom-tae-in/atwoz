package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.Survey;
import com.atwoz.survey.domain.survey.dto.SurveyCreateDto;

import java.util.List;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_선택_과목_질문_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_선택_과목_질문_하나_생성_요청;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_30개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyQuestionFixture.연애고사_질문_답변_두개_id_있음;
import static com.atwoz.survey.fixture.SurveyQuestionFixture.연애고사_질문_답변_한개_id_있음;

public class SurveyFixture {

    public static Survey 연애고사_필수_과목_질문_두개씩() {
        return Survey.from(SurveyCreateDto.from(연애고사_필수_과목_질문_두개씩_생성_요청()));
    }

    public static Survey 연애고사_필수_과목_질문_30개씩() {
        return Survey.from(SurveyCreateDto.from(연애고사_필수_과목_질문_30개씩_생성_요청()));
    }

    public static Survey 연애고사_선택_과목_질문_두개씩() {
        return Survey.from(SurveyCreateDto.from(연애고사_선택_과목_질문_두개씩_생성_요청()));
    }

    public static Survey 연애고사_선택_과목_질문_하나() {
        return Survey.from(SurveyCreateDto.from(연애고사_선택_과목_질문_하나_생성_요청()));
    }

    public static Survey 연애고사_필수_과목_질문_한개씩_전부_id_있음() {
        return Survey.builder()
                .id(1L)
                .required(true)
                .name("과목 1")
                .questions(List.of(연애고사_질문_답변_한개_id_있음()))
                .build();
    }

    public static Survey 연애고사_필수_과목_질문_두개씩_전부_id_있음() {
        return Survey.builder()
                .id(1L)
                .required(true)
                .name("과목 1")
                .questions(연애고사_질문_답변_두개_id_있음())
                .build();
    }
}
