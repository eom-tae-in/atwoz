package com.atwoz.admin.ui.auth.interceptor;

import com.atwoz.admin.domain.admin.AdminTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationContext;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AdminLoginValidCheckerInterceptorTest {

    private final HttpServletRequest req = mock(HttpServletRequest.class);
    private final HttpServletResponse res = mock(HttpServletResponse.class);
    private final AdminAuthenticationContext adminAuthenticationContext = mock(AdminAuthenticationContext.class);
    private final AdminAuthenticationExtractor adminAuthenticationExtractor = mock(AdminAuthenticationExtractor.class);
    private final AdminTokenProvider adminTokenProvider = mock(AdminTokenProvider.class);

    @Test
    void token이_없다면_예외를_발생한다() {
        // given
        AdminLoginValidCheckerInterceptor adminLoginValidCheckerInterceptor = new AdminLoginValidCheckerInterceptor(
                adminAuthenticationContext,
                adminAuthenticationExtractor,
                adminTokenProvider
        );
        when(req.getHeader("any")).thenReturn(null);

        // when & then
        assertThatThrownBy(() -> adminLoginValidCheckerInterceptor.preHandle(req, res, new Object()))
                .isInstanceOf(AdminLoginInvalidException.class);
    }
}
