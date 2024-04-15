package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.exception.exceptions.auth.LoginInvalidException;
import com.atwoz.member.ui.auth.support.auth.AuthenticationContext;
import com.atwoz.member.ui.auth.support.auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginValidCheckerInterceptor implements HandlerInterceptor {

    private static final String MEMBER_ID = "id";

    private final TokenProvider tokenProvider;
    private final AuthenticationContext authenticationContext;
    private final AuthenticationExtractor authenticationExtractor;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String token = authenticationExtractor.extractFromRequest(request)
                .orElseThrow(LoginInvalidException::new);

        Long extractedMemberId = tokenProvider.extract(token, MEMBER_ID, Long.class);
        authenticationContext.setAuthentication(extractedMemberId);

        return true;
    }
}
