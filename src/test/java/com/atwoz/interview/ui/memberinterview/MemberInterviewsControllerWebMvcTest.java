package com.atwoz.interview.ui.memberinterview;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewUpdateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.memberinterview.MemberInterview;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberInterviewsController.class)
class MemberInterviewsControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void 인터뷰에_응시한다() throws Exception {
        // given
        Long interviewId = 1L;
        MemberInterviewSubmitRequest request = new MemberInterviewSubmitRequest("답변");

        // when & then
        mockMvc.perform(post("/api/members/me/interviews/{interviewId}", interviewId)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andDo(customDocument("회원_인터뷰_응시",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("회원 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("interviewId").description("인터뷰 id")
                        ),
                        requestFields(
                                fieldWithPath("answer").description("인터뷰 답변")
                        )
                ));
    }

    @Test
    void 인터뷰_답변을_수정한다() throws Exception {
        // given
        Long interviewId = 1L;
        MemberInterviewUpdateRequest request = new MemberInterviewUpdateRequest("수정 답변");
        Interview interview = Interview.createWith("인터뷰 질문", "나");
        MemberInterview memberInterview = MemberInterview.createDefault(interview, "수정 답변");
        when(memberInterviewsService.updateMemberInterviewAnswer(any(), any(), any()))
                .thenReturn(memberInterview);

        // when & then
        mockMvc.perform(patch("/api/members/me/interviews/{interviewId}", interviewId)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_인터뷰_수정",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("회원 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("interviewId").description("인터뷰 id")
                        ),
                        requestFields(
                                fieldWithPath("answer").description("수정할 인터뷰 답변")
                        ),
                        responseFields(
                                fieldWithPath("id").description("인터뷰 id"),
                                fieldWithPath("answer").description("응시한 인터뷰 답변")
                        ))
                );
    }

    @Test
    void 인터뷰_답변을_조회한다() throws Exception {
        // given
        Long interviewId = 1L;
        when(memberInterviewsQueryService.findMemberInterviewAnswer(any(), any()))
                .thenReturn(new MemberInterviewDetailResponse(1L, "내가 생각하는 내 장점과 단점은 이거다!", "답변"));

        // when & then
        mockMvc.perform(get("/api/members/me/interviews/{interviewId}", interviewId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_인터뷰_단일_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("회원 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("interviewId").description("인터뷰 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("인터뷰 id"),
                                fieldWithPath("question").description("인터뷰 질문"),
                                fieldWithPath("answer").description("인터뷰 답변")
                        ))
                );
    }

    @Test
    void 인터뷰_답변_목록을_타입으로_조회한다() throws Exception {
        // given
        String type = "나";
        List<MemberInterviewSimpleResponse> responses = List.of(
                new MemberInterviewSimpleResponse(1L, "내가 생각하는 내 장점과 단점은 이거다!", true),
                new MemberInterviewSimpleResponse(2L, "나의 평일과 주말은 이런식으로 보내고 있어!", false),
                new MemberInterviewSimpleResponse(3L, "작고 귀여운 소소한 행복은 어떤게 있나요?", false)
        );
        when(memberInterviewsQueryService.findMemberInterviewsByType(any(), any())).thenReturn(responses);

        // when & then
        mockMvc.perform(get("/api/members/me/interviews")
                        .param("type", type)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("회원_인터뷰_목록_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("회원 토큰 정보")
                        ),
                        requestParts(
                                partWithName("type").description("조회할 인터뷰 타입 (기본 = '나') - '나', '관계', '연인')").optional()
                        ),
                        responseFields(
                                fieldWithPath("interviews").description("등록되어 있는 해당 타입의 모든 인터뷰"),
                                fieldWithPath("interviews[].id").description("인터뷰 id"),
                                fieldWithPath("interviews[].question").description("인터뷰 질문"),
                                fieldWithPath("interviews[].isSubmitted").description("인터뷰 응시 여부")
                        ))
                );
    }
}
