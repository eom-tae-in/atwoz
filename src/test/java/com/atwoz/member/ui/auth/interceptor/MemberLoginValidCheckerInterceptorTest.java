package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.exception.exceptions.auth.MemberLoginInvalidException;
import com.atwoz.member.ui.auth.support.MemberAuthenticationContext;
import com.atwoz.member.ui.auth.support.MemberAuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberLoginValidCheckerInterceptorTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    private final MemberAuthenticationContext memberAuthenticationContext = mock(MemberAuthenticationContext.class);
    private final MemberAuthenticationExtractor memberAuthenticationExtractor = mock(MemberAuthenticationExtractor.class);
    private final MemberTokenProvider memberTokenProvider = mock(MemberTokenProvider.class);

    @Test
    void token이_없다면_예외를_발생한다() {
        // given
        MemberLoginValidCheckerInterceptor memberLoginValidCheckerInterceptor = new MemberLoginValidCheckerInterceptor(
                memberAuthenticationContext,
                memberAuthenticationExtractor,
                memberTokenProvider
        );
        when(req.getHeader("any")).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> memberLoginValidCheckerInterceptor.preHandle(req, res, new Object()))
                .isInstanceOf(MemberLoginInvalidException.class);
    }
}
