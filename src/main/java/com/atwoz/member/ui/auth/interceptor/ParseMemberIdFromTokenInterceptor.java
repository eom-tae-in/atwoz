package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.ui.auth.support.auth.AuthenticationContext;
import com.atwoz.member.ui.auth.support.auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class ParseMemberIdFromTokenInterceptor implements HandlerInterceptor {

    private final LoginValidCheckerInterceptor loginValidCheckerInterceptor;
    private final AuthenticationContext authenticationContext;
    private final AuthenticationExtractor authenticationExtractor;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (authenticationExtractor.extractFromRequest(request).isEmpty()) {
            authenticationContext.setAnonymous();
            return true;
        }

        return loginValidCheckerInterceptor.preHandle(request, response, handler);
    }
}
