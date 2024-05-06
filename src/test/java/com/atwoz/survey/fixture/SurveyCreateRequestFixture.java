package com.atwoz.survey.fixture;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;

import java.util.List;

public class SurveyCreateRequestFixture {

    public static SurveyCreateRequest 설문_필수_질문_과목_두개씩_생성_요청() {
        return new SurveyCreateRequest("설문 제목", true, List.of(
                new SurveyQuestionCreateRequest("질문 내용1", List.of(
                        SurveyAnswerCreateRequest.of(1, "답변1"),
                        SurveyAnswerCreateRequest.of(2, "답변2")
                )),
                new SurveyQuestionCreateRequest("질문 내용2", List.of(
                        SurveyAnswerCreateRequest.of(1, "답변1"),
                        SurveyAnswerCreateRequest.of(2, "답변2")
                ))
        ));
    }
}
