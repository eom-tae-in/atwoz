package com.atwoz.member.application.auth;

import com.atwoz.alert.application.event.AlertTokenCreatedEvent;
import com.atwoz.global.event.Events;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.infrastructure.auth.OAuthFakeRequester;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.RecordApplicationEvents;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static com.atwoz.member.fixture.auth.OAuthProviderFixture.인증_기관_생성;

import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@RecordApplicationEvents
@ExtendWith(MockitoExtension.class)
class MemberAuthServiceTest {

    @Mock
    private MemberTokenProvider memberTokenProvider;
    private MemberAuthService memberAuthService;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setup() {
        memberAuthService = new MemberAuthService(memberTokenProvider, new OAuthFakeRequester(), new MemberFakeRepository());
        eventPublisher = mock(ApplicationEventPublisher.class);
        Events.setPublisher(eventPublisher);
    }

    @Test
    void 로그인을_진행하면_토큰을_반환한다() {
        // given
        LoginRequest loginRequest = new LoginRequest("kakao", "code", "token");
        OAuthProviderRequest oAuthProviderRequest = 인증_기관_생성();
        String expectedToken = "token";
        when(memberTokenProvider.createAccessToken(any())).thenReturn(expectedToken);

        // when
        String token = memberAuthService.login(loginRequest, oAuthProviderRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(token).isEqualTo(expectedToken);
            softly.assertThatCode(() -> verify(eventPublisher, times(1)).publishEvent(any(AlertTokenCreatedEvent.class)))
                    .doesNotThrowAnyException();
        });
    }
}
