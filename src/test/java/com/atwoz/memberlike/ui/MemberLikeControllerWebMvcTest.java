package com.atwoz.memberlike.ui;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.memberlike.application.MemberLikeQueryService;
import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.infrastructure.dto.MemberLikePagingResponse;
import com.atwoz.memberlike.infrastructure.dto.MemberLikeSimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.memberlike.fixture.MemberLikeFixture.호감_생성_id_주입;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberLikeController.class)
class MemberLikeControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberLikeQueryService memberLikeQueryService;

    @Test
    void 호감을_보낸다() throws Exception {
        // given
        MemberLikeCreateRequest request = new MemberLikeCreateRequest(2L, "관심있어요");

        // when & then
        mockMvc.perform(post("/api/members/me/likes")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("호감_전송",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("memberId").description("보낼 회원의 id"),
                                fieldWithPath("likeLevel").description("호감 정도")
                        )
                ));
    }

    @Test
    void 보낸_호감을_조회한다() throws Exception {
        // given
        List<MemberLikeSimpleResponse> details = new ArrayList<>();
        for (long id = 2; id <= 10; id++) {
            MemberLike memberLike = 호감_생성_id_주입(1L, id);
            MemberLikeSimpleResponse detail = new MemberLikeSimpleResponse(
                    id,
                    "회원 " + id,
                    "서울시",
                    "강남구",
                    20,
                    memberLike.getLikeIcon().getName(),
                    memberLike.getIsRecent()
            );
            details.add(detail);
        }

        when(memberLikeQueryService.findSendLikesWithPaging(any(), any(Pageable.class)))
                .thenReturn(new MemberLikePagingResponse(details, 1));

        // when & then
        mockMvc.perform(get("/api/members/me/likes/send")
                .param("page", "0")
                .param("size", "3")
                .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("보낸_호감_페이징조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("몇 개씩 조회를 할 것인지").optional()
                        ),
                        responseFields(
                                fieldWithPath("memberLikes").description("보낸 호감 목록"),
                                fieldWithPath("memberLikes[].memberId").description("내가 보낸 회원 id"),
                                fieldWithPath("memberLikes[].nickname").description("내가 보낸 회원의 닉네임"),
                                fieldWithPath("memberLikes[].city").description("내가 보낸 회원의 시/도"),
                                fieldWithPath("memberLikes[].sector").description("내가 보낸 회원의 구/동"),
                                fieldWithPath("memberLikes[].age").description("내가 보낸 회원의 나이"),
                                fieldWithPath("memberLikes[].likeIcon").description("호감 아이콘"),
                                fieldWithPath("memberLikes[].isRecent").description("48시간 이내의 호감 여부"),
                                fieldWithPath("nextPage").description("다음 페이지가 존재하면 1, 없다면 -1")
                        ))
                );
    }

    @Test
    void 받은_호감을_조회한다() throws Exception {
        // given
        List<MemberLikeSimpleResponse> details = new ArrayList<>();
        for (long id = 2; id <= 10; id++) {
            MemberLike memberLike = 호감_생성_id_주입(id, 1L);
            MemberLikeSimpleResponse detail = new MemberLikeSimpleResponse(
                    id,
                    "회원 " + id,
                    "서울시",
                    "강남구",
                    20,
                    memberLike.getLikeIcon().getName(),
                    memberLike.getIsRecent()
            );
            details.add(detail);
        }

        when(memberLikeQueryService.findReceivedLikesWithPaging(any(), any(Pageable.class)))
                .thenReturn(new MemberLikePagingResponse(details, 1));

        // when & then
        mockMvc.perform(get("/api/members/me/likes/receive")
                        .param("page", "0")
                        .param("size", "3")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("받은_호감_페이징조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestParts(
                                partWithName("page").description("페이지 번호").optional(),
                                partWithName("size").description("몇 개씩 조회를 할 것인지").optional()
                        ),
                        responseFields(
                                fieldWithPath("memberLikes").description("받은 호감 목록"),
                                fieldWithPath("memberLikes[].memberId").description("나에게 보낸 회원 id"),
                                fieldWithPath("memberLikes[].nickname").description("나에게 보낸 회원의 닉네임"),
                                fieldWithPath("memberLikes[].city").description("나에게 보낸 회원의 시/도"),
                                fieldWithPath("memberLikes[].sector").description("나에게 보낸 회원의 구/동"),
                                fieldWithPath("memberLikes[].age").description("나에게 보낸 회원의 나이"),
                                fieldWithPath("memberLikes[].likeIcon").description("호감 아이콘"),
                                fieldWithPath("memberLikes[].isRecent").description("48시간 이내의 호감 여부"),
                                fieldWithPath("nextPage").description("다음 페이지가 존재하면 1, 없다면 -1")
                        ))
                );
    }
}
