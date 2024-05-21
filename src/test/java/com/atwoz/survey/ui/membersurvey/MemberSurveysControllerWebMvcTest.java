package com.atwoz.survey.ui.membersurvey;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.survey.application.membersurvey.MemberSurveysQueryService;
import com.atwoz.survey.application.membersurvey.dto.SurveySubmitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.survey.fixture.MemberSurveyResponseFixture.회원_연애고사_응시_조회;
import static com.atwoz.survey.fixture.SurveySubmitRequestFixture.필수_과목_질문_두개_제출_요청;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberSurveysController.class)
class MemberSurveysControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberSurveysQueryService memberSurveysQueryService;

    @Test
    void 회원이_연애고사_과목을_응시한다() throws Exception {
        // given
        String bearerToken = "Bearer token";
        List<SurveySubmitRequest> requests = 필수_과목_질문_두개_제출_요청();

        // when & then
        mockMvc.perform(post("/api/members/me/surveys")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requests))
                        .header(AUTHORIZATION, bearerToken))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(customDocument("회원_연애고사_응시",
                        requestHeaders(
                                headerWithName(AUTHORIZATION)
                                        .description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("[].surveyId")
                                        .description("연애고사 과목 id"),
                                fieldWithPath("[].questions")
                                        .description("연애고사 과목 별 질문"),
                                fieldWithPath("[].questions.[].questionId")
                                        .description("각 질문 별 id"),
                                fieldWithPath("[].questions.[].answerNumber")
                                        .description("각 질문에 대한 답변 번호")
                        )
                ));
    }

    @Test
    void 회원이_응시한_연애고사를_과목으로_조회한다() throws Exception {
        // given
        Long surveyId = 1L;
        String bearerToken = "Bearer token";

        when(memberSurveysQueryService.findMemberSurvey(any(), any()))
                .thenReturn(회원_연애고사_응시_조회());

        // when & then
        mockMvc.perform(get("/api/members/me/surveys/" + surveyId)
                        .contentType("application/json")
                        .header(AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_연애고사_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION)
                                        .description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("surveyId").description("응시한 과목 id"),
                                fieldWithPath("questions").description("응시한 각 질문"),
                                fieldWithPath("questions[].questionId").description("응시한 각 질문의 id"),
                                fieldWithPath("questions[].answerNumber").description("응시한 각 질문의 답변 번호")
                        )
                ));
    }

    @Test
    void 회원과_답변이_30개_이상_같은_다른_회원을_조회한다() throws Exception {
        // given
        String bearerToken = "Bearer token";

        when(memberSurveysQueryService.findMatchMembers(any()))
                .thenReturn(List.of(2L));

        // when & then
        mockMvc.perform(get("/api/members/me/surveys/match")
                        .contentType("application/json")
                        .header(AUTHORIZATION, bearerToken))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_연애고사_매칭_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION)
                                        .description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("members").description("매칭된 회원들의 id")
                        )
                ));
    }
}
