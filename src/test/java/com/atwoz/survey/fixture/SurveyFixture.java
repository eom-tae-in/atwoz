package com.atwoz.survey.fixture;

import com.atwoz.survey.domain.survey.Survey;

public class SurveyFixture {

    public static Survey 설문_필수_질문_과목_두개씩() {
        return Survey.createWith(SurveyCreateRequestFixture.설문_필수_질문_과목_두개씩());
    }
}
