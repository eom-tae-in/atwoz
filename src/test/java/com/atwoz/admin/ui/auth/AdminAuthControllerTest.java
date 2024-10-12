package com.atwoz.admin.ui.auth;

import com.atwoz.admin.application.auth.AdminAuthService;
import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;
import com.atwoz.helper.MockBeanInjection;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.admin.fixture.AdminRequestFixture.관리자_로그인_요청;
import static com.atwoz.admin.fixture.AdminRequestFixture.관리자_회원_가입_요청;
import static com.atwoz.admin.fixture.AdminTokenResponseFixture.관리자_액세스_토큰_생성_응답;
import static com.atwoz.admin.fixture.AdminTokenResponseFixture.관리자_토큰_생성_응답;
import static com.atwoz.helper.RestDocsHelper.customDocument;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.COOKIE;
import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(AdminAuthController.class)
class AdminAuthControllerTest extends MockBeanInjection {

    private static final String BEARER_TOKEN = "Bearer token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AdminAuthService adminAuthService;

    @Test
    void 회원_가입을_진행한다() throws Exception {
        // given
        AdminSignUpRequest adminSignUpRequest = 관리자_회원_가입_요청();
        AdminTokenResponse adminTokenResponse = 관리자_토큰_생성_응답();
        when(adminAuthService.signUp(adminSignUpRequest)).thenReturn(adminTokenResponse);

        // when & then
        mockMvc.perform(post("/api/admins/auth/sign-up")
                        .content(objectMapper.writeValueAsString(adminSignUpRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(customDocument("관리자_회원가입",
                                requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호"),
                                        fieldWithPath("confirmPassword").description("비밀번호 확인"),
                                        fieldWithPath("name").description("이름"),
                                        fieldWithPath("phoneNumber").description("전화번호")
                                ),
                                responseHeaders(
                                        headerWithName(SET_COOKIE).description("발급된 리프레쉬 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("발급된 액세스 토큰")
                                )
                        )
                );
    }

    @Test
    void 로그인을_진행한다() throws Exception {
        // given
        AdminLoginRequest adminLoginRequest = 관리자_로그인_요청();
        AdminTokenResponse adminTokenResponse = 관리자_토큰_생성_응답();
        when(adminAuthService.login(adminLoginRequest)).thenReturn(adminTokenResponse);

        // when & then
        mockMvc.perform(post("/api/admins/auth/login")
                        .content(objectMapper.writeValueAsString(adminLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("관리자_로그인",
                                requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("password").description("비밀번호")
                                ),
                                responseHeaders(
                                        headerWithName(SET_COOKIE).description("발급된 리프레쉬 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("발급된 액세스 토큰")
                                )
                        )
                );
    }

    @Test
    void 액세스_토큰을_재발행한다() throws Exception {
        // given
        String refreshToken = "refreshToken";
        AdminAccessTokenResponse adminAccessTokenResponse = 관리자_액세스_토큰_생성_응답();
        when(adminAuthService.reGenerateAccessToken(any())).thenReturn(adminAccessTokenResponse);

        // when & then
        mockMvc.perform(post("/api/admins/auth/access-token-regeneration")
                        .header(HttpHeaders.COOKIE, refreshToken)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("관리자_액세스_토큰_재발행",
                                requestHeaders(
                                        headerWithName(COOKIE).description("이전에 발급받은 리프레쉬 토큰")
                                ),
                                responseFields(
                                        fieldWithPath("accessToken").description("지금 발급된 액세스 토큰")
                                )
                        )
                );
    }

    @Test
    void 로그아웃을_진행한다() throws Exception{
        // given
        String refreshToken = "refreshToken";

        // when & then
        mockMvc.perform(delete("/api/admins/auth/logout")
                        .header(HttpHeaders.COOKIE, refreshToken)
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("관리자_로그아웃",
                                requestHeaders(
                                        headerWithName(COOKIE).description("이전에 발급받은 리프레쉬 토큰")
                                )
                        )
                );
    }
}
