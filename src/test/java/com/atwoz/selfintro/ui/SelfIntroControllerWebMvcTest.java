package com.atwoz.selfintro.ui;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.selfintro.application.dto.SelfIntroCreateRequest;
import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.application.dto.SelfIntroQueryResponses;
import com.atwoz.selfintro.application.dto.SelfIntroUpdateRequest;
import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
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
import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_생성_요청_픽스처.셀프소개글_생성_요청서;
import static com.atwoz.selfintro.fixture.셀프소개글_요청_픽스처.셀프소개글_수정_요청_픽스처.셀프소개글_수정_요청서;
import static com.atwoz.selfintro.fixture.셀프소개글_응답_픽스처.셀프소개글_페이징_조회_응답_픽스처.셀프소개글_응답;
import static com.atwoz.selfintro.fixture.셀프소개글_픽스처.셀프소개글_생성_셀프소개글아이디;
import static org.mockito.Mockito.any;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(SelfIntroController.class)
class SelfIntroControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 셀프소개글을_저장한다() throws Exception {
        // given
        SelfIntroCreateRequest selfIntroCreateRequest = 셀프소개글_생성_요청서();

        // when & then
        mockMvc.perform(post("/api/self-intros/me")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(selfIntroCreateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("셀프소개글_저장",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("content").description("셀프소개글 내용")
                        )
                ));
    }

    @Test
    void 셀프소개글을_필터와_페이징으로_조회한다() throws Exception {
        // given
        List<SelfIntroQueryResponse> selfIntroQueryResponses = List.of(셀프소개글_응답());
        when(selfIntroQueryService.findAllSelfIntrosWithPagingAndFiltering(
                any(Pageable.class),
                any(SelfIntroFilterRequest.class),
                any()
        )).thenReturn(new SelfIntroQueryResponses(selfIntroQueryResponses, 0, 1, 2, 1));

        // when & then
        mockMvc.perform(get("/api/self-intros/filter")
                        .param("page", "0")
                        .param("size", "10")
                        .param("minAge", "20")
                        .param("maxAge", "30")
                        .param("isOnlyOppositeGender", "false")
                        .param("cities", "서울시,경기도")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("셀프소개글_페이징_조회(필터_적용)",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("조회되는 데이터 수, 한 페이지당 조회되는 데이터 수입니다.").optional(),
                                partWithName("minAge").description("최소 나이(필터)").optional(),
                                partWithName("maxAge").description("최대 나이(필터)").optional(),
                                partWithName("isOnlyOppositeGender").description("성별 설정(필터), 이 값이 true일 경우 이성만 조회됩니다.")
                                        .optional(),
                                partWithName("cities").description("선호 지역(필터), 지역명을 문자열로 제공해주시면 됩니다.").optional()
                        ),
                        responseFields(
                                fieldWithPath("selfIntros[].selfIntroId").description("셀프소개글 id"),
                                fieldWithPath("selfIntros[].selfIntroContent").description("셀프소개글"),
                                fieldWithPath("selfIntros[].nickname").description("작성자 닉네임"),
                                fieldWithPath("selfIntros[].city").description("작성자가 사는 도시"),
                                fieldWithPath("selfIntros[].age").description("작성자의 나이"),
                                fieldWithPath("selfIntros[].height").description("작성자의 키"),
                                fieldWithPath("nowPage").description("현재 페이지"),
                                fieldWithPath("nextPage").description("다음 페이지, 다음 페이지가 없을 경우 -1을 반환합니다."),
                                fieldWithPath("totalPages").description("전체 페이지 수"),
                                fieldWithPath("totalElements").description("전체 조회된 데이터 수")
                        )
                ));
    }

    @Test
    void 셀프소개글을_변경한다() throws Exception {
        // given
        SelfIntro selfIntro = 셀프소개글_생성_셀프소개글아이디(1L);
        SelfIntroUpdateRequest selfIntroUpdateRequest = 셀프소개글_수정_요청서();
        doNothing().when(selfIntroService)
                .updateSelfIntro(selfIntroUpdateRequest, selfIntro.getId(), selfIntro.getMemberId());

        // when & then
        mockMvc.perform(patch("/api/self-intros/{selfIntroId}", selfIntro.getId())
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(selfIntroUpdateRequest)))
                .andExpect(status().isOk())
                .andDo(customDocument("셀프소개글_변경",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("selfIntroId").description("셀프소개글 id")
                        ),
                        requestFields(
                                fieldWithPath("content").description("수정한 소개글")
                        )
                ));
    }

    @Test
    void 셀프소개글을_삭제한다() throws Exception {
        // given
        SelfIntro selfIntro = 셀프소개글_생성_셀프소개글아이디(1L);

        // when & then
        mockMvc.perform(delete("/api/self-intros/{selfIntroId}", selfIntro.getId())
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("셀프소개글_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("selfIntroId").description("셀프소개글 id")
                        )
                ));
    }
}
