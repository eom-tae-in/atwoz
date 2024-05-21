package com.atwoz.survey.fixture;

import com.atwoz.survey.application.membersurvey.dto.SurveyQuestionSubmitRequest;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;

import java.util.ArrayList;
import java.util.List;

public class SurveySubmitRequestFixture {

    public static List<SurveySubmitRequest> 필수_과목_질문_두개_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Long questionTwoId = 2L;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 필수_과목_질문_30개_제출_요청_전부_1번() {
        Long surveyId = 1L;
        List<SurveyQuestionSubmitRequest> questions = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            questions.add(new SurveyQuestionSubmitRequest((long) i, 1));
        }
        return List.of(new SurveySubmitRequest(surveyId, questions));
    }

    public static List<SurveySubmitRequest> 필수_과목_질문_30개_제출_요청_전부_2번() {
        Long surveyId = 1L;
        List<SurveyQuestionSubmitRequest> questions = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            questions.add(new SurveyQuestionSubmitRequest((long) i, 2));
        }
        return List.of(new SurveySubmitRequest(surveyId, questions));
    }

    public static List<SurveySubmitRequest> 선택_과목_질문_두개_제출_요청() {
        Long surveyId = 2L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Long questionTwoId = 2L;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 같은_필수_과목_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Long questionTwoId = 2L;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                )),
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 없는_과목_제출_요청() {
        Long surveyId = -1L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Long questionTwoId = 2L;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 선택_과목_없는_답변_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Integer answerNumber = -1;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 선택_과목_같은_질문_답변_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Integer answerOneNumber = 1;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionOneId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 선택_과목_없는_질문_답변_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Long questionTwoId = -1L;
        Integer answerOneNumber = 1;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }

    public static List<SurveySubmitRequest> 선택_과목_질문_답변_일부분만_제출_요청() {
        Long surveyId = 1L;
        Long questionOneId = 1L;
        Long questionTwoId = -1L;
        Integer answerOneNumber = 1;
        Integer answerTwoNumber = 2;

        return List.of(
                new SurveySubmitRequest(surveyId, List.of(
                        new SurveyQuestionSubmitRequest(questionOneId, answerOneNumber),
                        new SurveyQuestionSubmitRequest(questionTwoId, answerTwoNumber)
                ))
        );
    }
}
