package com.atwoz.hobby.ui;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.hobby.application.dto.HobbyCreateRequest;
import com.atwoz.hobby.application.dto.HobbyUpdateRequest;
import com.atwoz.hobby.infrasturcture.dto.HobbyPagingResponse;
import com.atwoz.hobby.infrasturcture.dto.HobbySingleResponse;
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
import static com.atwoz.hobby.fixture.취미_요청_픽스처.취미_생성_요청_픽스처.취미_생성_요청_생성;
import static com.atwoz.hobby.fixture.취미_요청_픽스처.취미_수정_요청_픽스처.취미_업데이트_요청_생성;
import static com.atwoz.hobby.fixture.취미_응답_픽스처.취미_단건_조회_응답_픽스처.취미_단건_조회_응답_생성;
import static com.atwoz.hobby.fixture.취미_응답_픽스처.취미_페이징_조회_응답_픽스처.취미_페이징_조회_목록_응답_생성_취미페이징목록응답;
import static com.atwoz.hobby.fixture.취미_응답_픽스처.취미_페이징_조회_응답_픽스처.취미_페이징_조회_응답_생성;
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
@WebMvcTest(HobbyController.class)
class HobbyControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 취미을_저장한다() throws Exception {
        // given
        HobbyCreateRequest hobbyCreateRequest = 취미_생성_요청_생성();
        doNothing().when(hobbyService)
                .saveHobby(hobbyCreateRequest);

        // when & then
        mockMvc.perform(post("/api/hobbies")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hobbyCreateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("취미_저장",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (취미 관련 설정은 관리자만 가능)")
                        ),
                        requestFields(
                                fieldWithPath("name").description("취미 이름"),
                                fieldWithPath("code").description("취미 코드")
                        )
                ));
    }

    @Test
    void 취미를_단건_조회한다() throws Exception {
        // given
        Long hobbyId = 1L;
        HobbySingleResponse hobbySingleResponse = 취미_단건_조회_응답_생성();
        when(hobbyQueryService.findHobby(anyLong())).thenReturn(hobbySingleResponse);

        // when
        mockMvc.perform(get("/api/hobbies/{hobbyId}", hobbyId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("취미_단건_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (취미 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("hobbyId").description("취미 id")
                        ),
                        responseFields(
                                fieldWithPath("name").description("조회된 취믜의 이름"),
                                fieldWithPath("code").description("조회된 취미의 코드")
                        )
                ));
    }

    @Test
    void 취미를_페이징_조회한다() throws Exception {
        // given
        List<HobbyPagingResponse> hobbyPagingResponse = List.of(취미_페이징_조회_응답_생성());
        when(hobbyQueryService.findHobbiesWithPaging(any(Pageable.class)))
                .thenReturn(취미_페이징_조회_목록_응답_생성_취미페이징목록응답(hobbyPagingResponse));

        // when & then
        mockMvc.perform(get("/api/hobbies")
                        .param("page", "0")
                        .param("size", "10")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("취미_페이징_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (취미 관련 설정은 관리자만 가능)")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("조회되는 데이터 수, 한 페이지당 조회되는 데이터 수입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("hobbyPagingResponses").description("취미 페이징 조회 결과 목록"),
                                fieldWithPath("hobbyPagingResponses[].hobbyId").description("조회된 취미의 id"),
                                fieldWithPath("hobbyPagingResponses[].name").description("조회된 취미의 이름"),
                                fieldWithPath("hobbyPagingResponses[].code").description("조회된 취미의 코드"),
                                fieldWithPath("nowPage").description("현재 페이지"),
                                fieldWithPath("nextPage").description("다음 페이지, 다음 페이지가 없을 경우 -1을 반환합니다."),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 조회된 데이터 수")
                        )));
    }

    @Test
    void 취미를_업데이트한다() throws Exception {
        // given
        Long hobbyId = 1L;
        HobbyUpdateRequest hobbyUpdateRequest = 취미_업데이트_요청_생성();
        doNothing().when(hobbyService)
                .updateHobby(hobbyId, hobbyUpdateRequest);

        // when & then
        mockMvc.perform(patch("/api/hobbies/{hobbyId}", hobbyId)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(hobbyUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("취미_업데이트",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (취미 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("hobbyId").description("취미 id")
                        ),
                        requestFields(
                                fieldWithPath("name").description("변경하려는 취미 이름"),
                                fieldWithPath("code").description("변경하려는 취미 코드")
                        )
                ));
    }

    @Test
    void 취미를_삭제한다() throws Exception {
        // given
        Long hobbyId = 1L;
        doNothing().when(hobbyService)
                .deleteHobby(hobbyId);

        // when & then
        mockMvc.perform(delete("/api/hobbies/{hobbyId}", hobbyId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("취미_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (취미 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("hobbyId").description("취미 id")
                        )
                ));
    }
}
