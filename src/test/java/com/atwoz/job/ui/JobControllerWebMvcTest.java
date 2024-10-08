package com.atwoz.job.ui;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.job.application.dto.JobCreateRequest;
import com.atwoz.job.application.dto.JobUpdateRequest;
import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.atwoz.job.infrasturcture.dto.JobSingleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.job.fixture.직업_요청_픽스처.직업_생성_요청_픽스처.직업_생성_요청_생성;
import static com.atwoz.job.fixture.직업_요청_픽스처.직업_수정_요청_픽스처.직업_업데이트_요청_생성;
import static com.atwoz.job.fixture.직업_응답_픽스처.직업_단건_조회_응답_픽스처.직업_단건_조회_응답_생성;
import static com.atwoz.job.fixture.직업_응답_픽스처.직업_페이징_조회_응답_픽스처.직업_페이징_조회_목록_응답_생성_직업페이징목록응답;
import static com.atwoz.job.fixture.직업_응답_픽스처.직업_페이징_조회_응답_픽스처.직업_페이징_조회_응답_생성;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(JobController.class)
class JobControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 직업을_저장한다() throws Exception {
        // given
        JobCreateRequest jobCreateRequest = 직업_생성_요청_생성();
        doNothing().when(jobService)
                .saveJob(jobCreateRequest);

        // when & then
        mockMvc.perform(post("/api/jobs")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobCreateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("직업_저장",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (직업 관련 설정은 관리자만 가능)")
                        ),
                        requestFields(
                                fieldWithPath("name").description("직업 이름"),
                                fieldWithPath("code").description("직업 코드")
                        )
                ));
    }

    @Test
    void 직업을_단건_조회한다() throws Exception {
        // given
        Long jobId = 1L;
        JobSingleResponse response = 직업_단건_조회_응답_생성();
        when(jobQueryService.findJob(anyLong())).thenReturn(response);

        // when
        mockMvc.perform(get("/api/jobs/{jobId}", jobId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("직업_단건_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (직업 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("jobId").description("직업 id")
                        ),
                        responseFields(
                                fieldWithPath("name").description("조회된 직업의 이름"),
                                fieldWithPath("code").description("조회된 직업의 코드")
                        )
                ));
    }

    @Test
    void 직업을_페이징_조회한다() throws Exception {
        // given
        List<JobPagingResponse> jobPagingResponses = List.of(직업_페이징_조회_응답_생성());
        when(jobQueryService.findJobsWithPaging(any(Pageable.class)))
                .thenReturn(직업_페이징_조회_목록_응답_생성_직업페이징목록응답(jobPagingResponses));

        // when & then
        mockMvc.perform(get("/api/jobs")
                        .param("page", "0")
                        .param("size", "10")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("직업_페이징_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (직업 관련 설정은 관리자만 가능)")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("조회되는 데이터 수, 한 페이지당 조회되는 데이터 수입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("jobPagingResponses").description("직업 페이징 조회 결과 목록"),
                                fieldWithPath("jobPagingResponses[].jobId").description("조회된 직업의 id"),
                                fieldWithPath("jobPagingResponses[].name").description("조회된 직업의 이름"),
                                fieldWithPath("jobPagingResponses[].code").description("조회된 직업의 코드"),
                                fieldWithPath("nowPage").description("현재 페이지"),
                                fieldWithPath("nextPage").description("다음 페이지, 다음 페이지가 없을 경우 -1을 반환합니다."),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 조회된 데이터 수")
                        )
                ));
    }

    @Test
    void 직업을_업데이트한다() throws Exception {
        // given
        Long jobId = 1L;
        JobUpdateRequest jobUpdateRequest = 직업_업데이트_요청_생성();
        doNothing().when(jobService)
                .updateJob(jobId, jobUpdateRequest);

        // when & then
        mockMvc.perform(patch("/api/jobs/{jobId}", jobId)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("직업_업데이트",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (직업 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("jobId").description("직업 id")
                        ),
                        requestFields(
                                fieldWithPath("name").description("변경하려는 직업 이름"),
                                fieldWithPath("code").description("변경하려는 직업 코드")
                        )
                ));
    }

    @Test
    void 직업을_삭제한다() throws Exception {
        // given
        Long jobId = 1L;
        doNothing().when(jobService)
                .deleteJob(jobId);

        // when & then
        mockMvc.perform(delete("/api/jobs/{jobId}", jobId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("직업_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (직업 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("jobId").description("직업 id")
                        )
                ));
    }
}
