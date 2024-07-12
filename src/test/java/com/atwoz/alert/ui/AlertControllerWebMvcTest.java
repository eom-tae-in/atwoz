package com.atwoz.alert.ui;

import com.atwoz.alert.application.AlertQueryService;
import com.atwoz.alert.application.AlertService;
import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.vo.AlertGroup;
import com.atwoz.alert.infrastructure.dto.AlertContentSearchResponse;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.atwoz.helper.MockBeanInjection;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_제목_날짜_id_주입;
import static com.atwoz.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
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
@WebMvcTest(AlertController.class)
class AlertControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlertService alertService;

    @Autowired
    private AlertQueryService alertQueryService;

    @Test
    void 알림을_페이징_조회한다() throws Exception {
        // given
        List<AlertSearchResponse> details = new ArrayList<>();
        for (long id = 1; id <= 10; id++) {
            Alert alert = 알림_생성_제목_날짜_id_주입("알림 제목 " + id, (int) id, id);
            AlertSearchResponse detail = new AlertSearchResponse(
                    id,
                    AlertGroup.ALERT,
                    new AlertContentSearchResponse(alert.getTitle(), alert.getBody()),
                    alert.getIsRead(),
                    alert.getCreatedAt()
            );
            details.add(detail);
        }
        List<AlertSearchResponse> sorted = details.stream()
                .sorted(Comparator.comparing(AlertSearchResponse::createdAt)
                        .reversed()
                        .thenComparing(Comparator.comparing(AlertSearchResponse::id).reversed()))
                .toList();
        when(alertQueryService.findMemberAlertsWithPaging(any(), any(Pageable.class)))
                .thenReturn(new AlertPagingResponse(sorted, 1));

        // when & then
        mockMvc.perform(get("/api/members/me/alerts")
                .param("page", "0")
                .param("size", "3")
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("받은_알림_페이징조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("몇 개씩 조회를 할 것인지").optional()
                        ),
                        responseFields(
                                fieldWithPath("alerts").description("받은 알림 목록"),
                                fieldWithPath("alerts[].id").description("알림 id"),
                                fieldWithPath("alerts[].group").description("알림의 그룹"),
                                fieldWithPath("alerts[].alert").description("알림의 실제 내용"),
                                fieldWithPath("alerts[].alert.title").description("알림의 제목"),
                                fieldWithPath("alerts[].alert.body").description("알림의 본문 (선택)"),
                                fieldWithPath("alerts[].isRead").description("알림 읽음 여부"),
                                fieldWithPath("alerts[].createdAt").description("알림 생성 시각"),
                                fieldWithPath("nextPage").description("다음 페이지가 존재하면 1, 없다면 -1")
                        ))
                );
    }

    @Test
    void 알림을_단일_조회한다() throws Exception {
        // given
        long id = 1L;
        Alert alert = 알림_생성_제목_날짜_id_주입("알림 제목", (int) id, id);
        alert.read();

        when(alertService.readAlert(any(), any())).thenReturn(alert);

        // when & then
        mockMvc.perform(get("/api/members/me/alerts/{alertId}", id)
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("단일_알림_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("alertId").description("읽고자 하는 알림 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("알림 id"),
                                fieldWithPath("group").description("알림의 그룹"),
                                fieldWithPath("alert").description("알림의 실제 내용"),
                                fieldWithPath("alert.title").description("알림의 제목"),
                                fieldWithPath("alert.body").description("알림의 본문 (선택)"),
                                fieldWithPath("isRead").description("알림 읽음 여부"),
                                fieldWithPath("createdAt").description("알림 생성 시각")
                        ))
                );

    }
}
