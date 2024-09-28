package com.atwoz.interview.ui.interview;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.interview.application.interview.InterviewQueryService;
import com.atwoz.interview.application.interview.InterviewService;
import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
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
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_타입_질문_id;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(InterviewController.class)
class InterviewControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InterviewService interviewService;

    @Autowired
    private InterviewQueryService interviewQueryService;

    @Test
    void 인터뷰를_등록한다() throws Exception {
        // given
        String question = "내 최애 음식과 싫어하는 음식은?";
        InterviewCreateRequest request = new InterviewCreateRequest(question, "나");
        when(interviewService.createInterview(request)).thenReturn(인터뷰_타입_질문_id(InterviewType.ME, question, 1L));

        // when & then
        mockMvc.perform(post("/api/interviews")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(customDocument("인터뷰_등록",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("어드민 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("question").description("인터뷰 질문"),
                                fieldWithPath("type").description("등록할 인터뷰 타입")
                        ),
                        responseHeaders(
                                headerWithName(LOCATION).description("Location 헤더")
                        ),
                        responseFields(
                                fieldWithPath("question").description("등록된 인터뷰 질문"),
                                fieldWithPath("type").description("등록된 인터뷰 타입")
                        )
                ));

    }

    @Test
    void 인터뷰를_수정한다() throws Exception {
        // given
        Long id = 1L;
        String question = "내 최애 음식과 싫어하는 음식은?";
        String updatedQuestion = "변경된 관계 질문";
        InterviewUpdateRequest request = new InterviewUpdateRequest(question, "나");
        when(interviewService.updateInterview(id, request)).thenReturn(인터뷰_타입_질문_id(InterviewType.RELATIONSHIP, updatedQuestion, id));

        // when & then
        mockMvc.perform(patch("/api/interviews/{interviewId}", id)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("인터뷰_수정",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("어드민 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("interviewId").description("인터뷰 id")
                        ),
                        requestFields(
                                fieldWithPath("question").description("변경할 인터뷰 질문"),
                                fieldWithPath("type").description("변경할 인터뷰 타입")
                        ),
                        responseFields(
                                fieldWithPath("id").description("인터뷰 id"),
                                fieldWithPath("question").description("변경된 인터뷰 질문"),
                                fieldWithPath("type").description("변경된 인터뷰 타입")
                        )
                ));
    }

    @Test
    void 인터뷰_목록을_타입으로_조회한다() throws Exception {
        // given
        String type = "나";
        List<InterviewResponse> response = List.of(
                new InterviewResponse(1L, "내가 생각하는 내 장점과 단점은 이거다!", InterviewType.ME),
                new InterviewResponse(2L, "나의 평일과 주말은 이런식으로 보내고 있어!", InterviewType.ME),
                new InterviewResponse(3L, "작고 귀여운 소소한 행복은 어떤게 있나요?", InterviewType.ME)
        );
        when(interviewQueryService.findInterviewsByType(type)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/interviews")
                        .param("type", type)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("인터뷰_목록_타입_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("어드민 토큰 정보")
                        ),
                        requestParts(
                                partWithName("type").description("조회할 인터뷰 타입 (기본 = '나') - '나', '관계', '연인')").optional()
                        ),
                        responseFields(
                                fieldWithPath("interviews").description("조회된 인터뷰 목록"),
                                fieldWithPath("interviews[].id").description("인터뷰 id"),
                                fieldWithPath("interviews[].question").description("인터뷰 질문"),
                                fieldWithPath("interviews[].type").description("인터뷰 타입 (ENUM)")
                        ))
                );
    }
}
