package com.atwoz.survey.fixture;

import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.atwoz.survey.domain.membersurvey.dto.SurveySubmitCreateDto;

import java.util.List;

public class SurveySubmitCreateDtoFixture {

    public static List<SurveySubmitCreateDto> 필수_과목_질문_두개_제출_요청() {
        List<SurveySubmitRequest> requests = SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청();
        return requests.stream()
                .map(SurveySubmitCreateDto::from)
                .toList();
    }
}
