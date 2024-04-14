package com.atwoz.member.application.auth;

import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.infrastructure.auth.OAuthFakeRequester;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.atwoz.member.fixture.OAuthProviderFixture.인증_기관_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private TokenProvider tokenProvider;
    private AuthService authService;

    @BeforeEach
    void setup() {
        authService = new AuthService(tokenProvider, new OAuthFakeRequester());
    }

    @Test
    void 로그인을_진행하면_토큰을_반환한다() {
        // given
        LoginRequest loginRequest = new LoginRequest("kakao", "code");
        OAuthProviderRequest oAuthProviderRequest = 인증_기관_생성();
        String expectedToken = "token";
        when(tokenProvider.createTokenWithPhoneNumber(anyString())).thenReturn(expectedToken);

        // when
        String token = authService.login(loginRequest, oAuthProviderRequest);

        // then
        assertThat(token).isEqualTo(expectedToken);
    }
}
