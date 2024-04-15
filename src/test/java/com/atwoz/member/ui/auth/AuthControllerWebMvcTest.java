package com.atwoz.member.ui.auth;

import com.atwoz.helper.MockBeanInjection;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.atwoz.helper.RestDocsHelper.customDocument;
import static com.atwoz.member.fixture.OAuthProviderFixture.인증_기관_생성;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@AutoConfigureRestDocs
@WebMvcTest(AuthController.class)
class AuthControllerWebMvcTest extends MockBeanInjection {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void 로그인을_진행한다() throws Exception {
        // given
        OAuthProviderRequest oAuthProviderRequest = 인증_기관_생성();
        LoginRequest loginRequest = new LoginRequest("kakao", "code");
        String expectedToken = "token";
        when(authService.login(loginRequest, oAuthProviderRequest)).thenReturn(expectedToken);

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                ).andExpect(status().isOk())
                .andDo(print())
                .andDo(customDocument("do_signup",
                        requestFields(
                                fieldWithPath("provider").description("인증기관"),
                                fieldWithPath("code").description("인증코드")
                        ),
                        responseFields(
                                fieldWithPath("token").description("발급되는 토큰")
                        )
                ));
    }
}
