package com.atwoz.member.ui.member.profile.style;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.member.profile.style.dto.StyleCreateRequest;
import com.atwoz.member.application.member.profile.style.dto.StyleUpdateRequest;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
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
import static com.atwoz.member.fixture.member.dto.request.StyleCreateRequestFixture.스타일_생성_요청_생성;
import static com.atwoz.member.fixture.member.dto.request.StyleUpdateRequestFixture.스타일_업데이트_요청_생성;
import static com.atwoz.member.fixture.member.dto.response.StyleResponseFixture.스타일_응답_생성;
import static com.atwoz.member.fixture.member.dto.response.StyleResponsesFixture.스타일_응답_목록_생성_스타일응답목록;
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
@WebMvcTest(StyleController.class)
class StyleControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 스타일을_저장한다() throws Exception {
        // given
        StyleCreateRequest styleCreateRequest = 스타일_생성_요청_생성();
        doNothing().when(styleService)
                .saveStyle(styleCreateRequest);

        // when & then
        mockMvc.perform(post("/api/members/styles")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(styleCreateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("스타일_저장",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (스타일 관련 설정은 관리자만 가능)")
                        ),
                        requestFields(
                                fieldWithPath("name").description("스타일 이름"),
                                fieldWithPath("code").description("스타일 코드")
                        )
                ));
    }

    @Test
    void 스타일을_단건_조회한다() throws Exception {
        // given
        Long styleId = 1L;
        StyleResponse styleResponse = 스타일_응답_생성();
        when(styleQueryService.findStyle(anyLong())).thenReturn(styleResponse);

        // when
        mockMvc.perform(get("/api/members/styles/{styleId}", styleId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("스타일_단건_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (스타일 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("styleId").description("스타일 id")
                        ),
                        responseFields(
                                fieldWithPath("name").description("조회된 스타일의 이름"),
                                fieldWithPath("code").description("조회된 스타일의 코드")
                        )
                ));
    }

    @Test
    void 스타일을_페이징_조회한다() throws Exception {
        // given
        List<StyleResponse> styleResponses = List.of(스타일_응답_생성());
        when(styleQueryService.findStylesWithPaging(any(Pageable.class)))
                .thenReturn(스타일_응답_목록_생성_스타일응답목록(styleResponses));

        // when & then
        mockMvc.perform(get("/api/members/styles")
                        .param("page", "0")
                        .param("size", "10")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(customDocument("스타일_페이징_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (스타일 관련 설정은 관리자만 가능)")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("조회되는 데이터 수, 한 페이지당 조회되는 데이터 수입니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("styleResponses").description("조회된 스타일 목록"),
                                fieldWithPath("styleResponses[].name").description("조회된 스타일의 이름"),
                                fieldWithPath("styleResponses[].code").description("조회된 스타일의 코드"),
                                fieldWithPath("nowPage").description("현재 페이지"),
                                fieldWithPath("nextPage").description("다음 페이지, 다음 페이지가 없을 경우 -1을 반환합니다."),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 조회된 데이터 수")
                        )
                ));
    }

    @Test
    void 스타일을_업데이트한다() throws Exception {
        // given
        Long styleId = 1L;
        StyleUpdateRequest styleUpdateRequest = 스타일_업데이트_요청_생성();
        doNothing().when(styleService)
                .updateStyle(styleId, styleUpdateRequest);

        // when & then
        mockMvc.perform(patch("/api/members/styles/{styleId}", styleId)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(styleUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("스타일_업데이트",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (스타일 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("styleId").description("스타일 id")
                        ),
                        requestFields(
                                fieldWithPath("name").description("변경하려는 스타일 이름"),
                                fieldWithPath("code").description("변경하려는 스타일 코드")
                        )
                ));
    }

    @Test
    void 스타일을_삭제한다() throws Exception {
        // given
        Long styleId = 1L;
        doNothing().when(styleService)
                .deleteStyle(styleId);

        // when & then
        mockMvc.perform(delete("/api/members/styles/{styleId}", styleId)
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("스타일_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("관리자 토큰 정보 (스타일 관련 설정은 관리자만 가능)")
                        ),
                        pathParameters(
                                parameterWithName("styleId").description("스타일 id")
                        )
                ));
    }
}
