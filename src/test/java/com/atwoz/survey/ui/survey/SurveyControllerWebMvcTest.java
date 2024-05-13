package com.atwoz.survey.ui.survey;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.survey.application.survey.SurveyService;
import com.atwoz.survey.application.survey.dto.SurveyCreateRequest;
import com.atwoz.survey.domain.survey.Survey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.survey.fixture.SurveyCreateRequestFixture.연애고사_필수_과목_질문_두개씩_생성_요청;
import static com.atwoz.survey.fixture.SurveyFixture.연애고사_필수_과목_질문_두개씩_전부_id_있음;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(SurveyController.class)
class SurveyControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SurveyService surveyService;

    @Test
    void 연애고사_과목을_생성한다() throws Exception {
        // given
        SurveyCreateRequest request = 연애고사_필수_과목_질문_두개씩_생성_요청();
        Survey survey = 연애고사_필수_과목_질문_두개씩_전부_id_있음();
        when(surveyService.addSurvey(request)).thenReturn(survey);

        // when
        mockMvc.perform(post("/api/surveys")
                        .header(HttpHeaders.AUTHORIZATION, "bearer tokenInfo...")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                        .andExpect(status().isCreated())
                .andDo(print())
                .andDo(customDocument("연애_모의고사_과목_생성",
                        requestFields(
                                fieldWithPath("name").description("과목 제목"),
                                fieldWithPath("required").description("필수 여부"),
                                fieldWithPath("questions").description("질문 목록"),
                                fieldWithPath("questions[].description").description("질문 내용"),
                                fieldWithPath("questions[].answers").description("답변 목록"),
                                fieldWithPath("questions[].answers[].number").description("답변 번호"),
                                fieldWithPath("questions[].answers[].answer").description("답변 내용")
                        ),
                        responseHeaders(
                                headerWithName("location").description("생성된 과목 경로")
                        ),
                        responseFields(
                                fieldWithPath("surveyId").description("연애고사 과목 id"),
                                fieldWithPath("questions").description("연애고사 과목의 질문"),
                                fieldWithPath("questions[].questionId").description("연애고사 질문의 id"),
                                fieldWithPath("questions[].answerNumbers").description("연애고사 질문에서 가능한 답변 선택지 번호")
                        )
                ));
    }
}
