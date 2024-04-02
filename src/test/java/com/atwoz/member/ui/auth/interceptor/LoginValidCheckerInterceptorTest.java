package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.exception.exceptions.auth.LoginInvalidException;
import com.atwoz.member.infrastructure.auth.JwtTokenProvider;
import com.atwoz.member.infrastructure.member.MemberFakeRepository;
import com.atwoz.member.ui.auth.support.auth.AuthenticationContext;
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
class LoginValidCheckerInterceptorTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);

    @Test
    void token이_없다면_예외를_발생한다() {
        // given
        LoginValidCheckerInterceptor loginValidCheckerInterceptor = new LoginValidCheckerInterceptor(
                new JwtTokenProvider(),
                new AuthenticationContext(),
                new MemberFakeRepository()
        );

        when(req.getHeader("any")).thenReturn(null);

        // when
        assertThatThrownBy(() -> loginValidCheckerInterceptor.preHandle(req, res, new Object()))
                .isInstanceOf(LoginInvalidException.class);
    }
}
