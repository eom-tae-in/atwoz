package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.auth.InvalidJsonKeyException;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.ui.auth.support.auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class TokenRegenerateInterceptorTest {

    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final ContentCachingResponseWrapper response = mock(ContentCachingResponseWrapper.class);
    private final TokenProvider tokenProvider = mock(TokenProvider.class);
    private final JsonMapper jsonMapper = mock(JsonMapper.class);
    private final MemberRepository memberRepository = new MemberFakeRepository();
    private final AuthenticationExtractor authenticationExtractor = mock(AuthenticationExtractor.class);

    private TokenRegenerateInterceptor tokenRegenerateInterceptor;

    @BeforeEach
    void setup() {
        tokenRegenerateInterceptor = new TokenRegenerateInterceptor(
                tokenProvider,
                memberRepository,
                jsonMapper,
                authenticationExtractor
        );
    }

    @Test
    void token이_없으면_예외가_발생한다() {
        // given
        when(authenticationExtractor.extractFromResponse(response)).thenThrow(InvalidJsonKeyException.class);

        // when & then
        assertThatThrownBy(
                () -> tokenRegenerateInterceptor.postHandle(request, response, new Object(), new ModelAndView()))
                .isInstanceOf(InvalidJsonKeyException.class);
    }
}
