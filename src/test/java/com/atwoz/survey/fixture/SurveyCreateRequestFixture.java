package com.atwoz.survey.fixture;

import com.atwoz.survey.application.survey.dto.SurveyAnswerCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.application.survey.dto.SurveyQuestionCreateRequest;

import java.util.ArrayList;
import java.util.List;

public class SurveyCreateRequestFixture {

    public static SurveyCreateRequest 연애고사_필수_과목_질문_두개씩_생성_요청() {
        return new SurveyCreateRequest("과목 제목", true, List.of(
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

    public static SurveyCreateRequest 연애고사_필수_과목_질문_30개씩_생성_요청() {
        List<SurveyQuestionCreateRequest> questions = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            questions.add(new SurveyQuestionCreateRequest("질문 내용" + i, List.of(
                    SurveyAnswerCreateRequest.of(1, "답변1"),
                    SurveyAnswerCreateRequest.of(2, "답변2"),
                    SurveyAnswerCreateRequest.of(3, "답변3"),
                    SurveyAnswerCreateRequest.of(4, "답변4")
            )));
        }
        return new SurveyCreateRequest("과목 제목", true, questions);
    }

    public static SurveyCreateRequest 연애고사_선택_과목_질문_두개씩_생성_요청() {
        return new SurveyCreateRequest("선택 과목 제목", false, List.of(
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

    public static SurveyCreateRequest 연애고사_필수_과목_질문_중복() {
        return new SurveyCreateRequest("설문 제목", true, List.of(
                new SurveyQuestionCreateRequest("질문1", List.of(
                        SurveyAnswerCreateRequest.of(1, "답1"),
                        SurveyAnswerCreateRequest.of(2, "답2")
                )),
                new SurveyQuestionCreateRequest("질문1", List.of(
                        SurveyAnswerCreateRequest.of(1, "답1"),
                        SurveyAnswerCreateRequest.of(2, "답2")
                ))
        ));
    }

    public static SurveyCreateRequest 연애고사_필수_과목_질문_번호_음수() {
        return new SurveyCreateRequest("과목 제목", true, List.of(
                new SurveyQuestionCreateRequest("질문1", List.of(
                        SurveyAnswerCreateRequest.of(-1, "답변 1")
                ))
        ));
    }

    public static SurveyCreateRequest 연애고사_필수_과목_하나_생성() {
        return new SurveyCreateRequest("과목 1", true, List.of(
                new SurveyQuestionCreateRequest("질문 1", List.of(
                        SurveyAnswerCreateRequest.of(1, "답변 1")
                ))
        ));
    }

    public static SurveyCreateRequest 연애고사_선택_과목_질문_하나_생성_요청() {
        return new SurveyCreateRequest("과목 1", false, List.of(
                new SurveyQuestionCreateRequest("질문 1", List.of(
                        SurveyAnswerCreateRequest.of(1, "답변 1")
                ))
        ));
    }
}
