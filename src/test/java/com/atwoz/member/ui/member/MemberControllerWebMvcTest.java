package com.atwoz.member.ui.member;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.domain.member.vo.ContactType;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_계정_상태_변경_요청_픽스처.회원_계정_상태_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_연락처_정보_변경_요청_픽스처.회원_연락처_정보_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_요청_픽스처.회원_푸시_알림_변경_요청_픽스처.회원_푸시_알림_변경_요청_생성;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_계정_상태_조회_응답_픽스처.회원_계정_상태_조회_응답_생성;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_조회_응답_픽스처.회원_연락처_정보_조회_응답_생성;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_연락처_정보_중복_검증_응답_픽스처.회원_연락처_정보_중복_검증_응답_생성;
import static com.atwoz.member.fixture.member.회원_응답_픽스처.회원_푸시_알림_조회_응답_픽스처.회원_푸시_알림_조회_응답_생성;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
class MemberControllerWebMvcTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 회원의_푸시_알림_정보를_조회한다() throws Exception {
        // given
        MemberNotificationsResponse response = 회원_푸시_알림_조회_응답_생성();
        when(memberQueryService.findMemberNotifications(any())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/members/me/notifications")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_푸시_알림_정보_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("isLikeReceivedNotificationOn").description("내가받은 호감 푸시 일림 정보"),
                                fieldWithPath("isNewMessageNotificationOn").description("새 메세지 푸시 일림 정보"),
                                fieldWithPath("isProfileExchangeNotificationOn").description("프로필 교환 요청 푸시 일림 정보"),
                                fieldWithPath("isProfileImageChangeNotificationOn").description("프로필 사진 변경 푸시 일림 정보"),
                                fieldWithPath("isLongTimeLoLoginNotificationOn").description("장기간 미접속 안내 푸시 일림 정보"),
                                fieldWithPath("isInterviewWritingRequestNotificationOn")
                                        .description("인터뷰 작성 권유 푸시 일림 정보")
                        )
                ));
    }

    @Test
    void 회원의_계정_상태를_조회한다() throws Exception {
        // given
        MemberAccountStatusResponse response = 회원_계정_상태_조회_응답_생성();
        when(memberQueryService.findMemberAccountStatus(any())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/members/me/statuses/account")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_계정_상태_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("status").description("회원 계정 상태")
                        )
                ));
    }

    @Test
    void 회원의_연락처_정보를_조회한다() throws Exception {
        // given
        MemberContactInfoResponse response = 회원_연락처_정보_조회_응답_생성();
        when(memberQueryService.findMemberContactInfo(any())).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/members/me/contact-info")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_연락처_정보_조회",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        responseFields(
                                fieldWithPath("contactType").description("연락처 타입 ex) 휴대폰 번호"),
                                fieldWithPath("contactValue").description("연락처 정보")
                        )
                ));
    }

    @Test
    void 연락처_정보가_중복되는지_확인한다() throws Exception {
        // given
        String contactType = ContactType.PHONE_NUMBER.getType();
        String contactValue = "01012345678";
        MemberContactInfoDuplicationCheckResponse response = 회원_연락처_정보_중복_검증_응답_생성();
        when(memberQueryService.checkContactInfoDuplicated(contactType, contactValue)).thenReturn(response);

        // when & then
        mockMvc.perform(get("/api/members/{contactType}/{contactValue}/duplication", contactType, contactValue)
                        .header(AUTHORIZATION, BEARER_TOKEN)
                ).andExpect(status().isOk())
                .andDo(customDocument("연락처_정보_중복_확인",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        pathParameters(
                                parameterWithName("contactType").description("연락처 타입 ex) 휴대폰 번호"),
                                parameterWithName("contactValue").description("연락처 정보")
                        ),
                        responseFields(
                                fieldWithPath("isDuplicated").description("연락처 정보의 중복 여부")
                        )
                ));
    }

    @Test
    void 회원의_푸시_알림_설정을_변경한다() throws Exception {
        // given
        MemberNotificationsPatchRequest request = 회원_푸시_알림_변경_요청_생성();
        doNothing().when(memberService).patchMemberNotifications(anyLong(), any(MemberNotificationsPatchRequest.class));

        // when & then
        mockMvc.perform(patch("/api/members/me/notifications")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_푸시_알림_설정_변경",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("isLikeReceivedNotificationOn").description("내가받은 호감 푸시 일림 정보"),
                                fieldWithPath("isNewMessageNotificationOn").description("새 메세지 푸시 일림 정보"),
                                fieldWithPath("isProfileExchangeNotificationOn").description("프로필 교환 요청 푸시 일림 정보"),
                                fieldWithPath("isProfileImageChangeNotificationOn").description("프로필 사진 변경 푸시 일림 정보"),
                                fieldWithPath("isLongTimeLoLoginNotificationOn").description("장기간 미접속 안내 푸시 일림 정보"),
                                fieldWithPath("isInterviewWritingRequestNotificationOn")
                                        .description("인터뷰 작성 권유 푸시 일림 정보")
                        )
                ));
    }

    @Test
    void 회원의_계정_상태를_변경한다() throws Exception {
        // given
        MemberAccountStatusPatchRequest request = 회원_계정_상태_변경_요청_생성();
        doNothing().when(memberService).patchMemberAccountStatus(anyLong(), any(MemberAccountStatusPatchRequest.class));

        // when & then
        mockMvc.perform(patch("/api/members/me/statuses/account")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_계정_상태_변경",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("status").description("변경할 회원의 상태")
                        )
                ));
    }

    @Test
    void 회원의_연락처_정보를_변경한다() throws Exception {
        // given
        MemberContactInfoPatchRequest request = 회원_연락처_정보_변경_요청_생성();
        doNothing().when(memberService).patchMemberContact(any(), any(MemberContactInfoPatchRequest.class));

        // when
        mockMvc.perform(patch("/api/members/me/contact-info")
                        .header(AUTHORIZATION, BEARER_TOKEN)
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("회원_연락처_정보_변경",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("유저 토큰 정보")
                        ),
                        requestFields(
                                fieldWithPath("contactType").description("연락처 타입 ex) 휴대폰 번호"),
                                fieldWithPath("contactValue").description("연락처 정보")
                        )
                ));
    }

    @Test
    void 회원을_삭제한다() throws Exception {
        // given
        doNothing().when(memberService).deleteMember(any());

        // when & then
        mockMvc.perform(delete("/api/members/me")
                        .header(AUTHORIZATION, BEARER_TOKEN))
                .andExpect(status().isNoContent())
                .andDo(customDocument("회원_삭제",
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("인증 토큰 정보")
                        )
                ));
    }
}
