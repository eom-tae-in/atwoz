package com.atwoz.admin.ui.auth.interceptor;

import com.atwoz.admin.ui.auth.support.AdminAuthenticationContext;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class ParseAdminIdFromTokenInterceptor implements HandlerInterceptor {

    private final AdminLoginValidCheckerInterceptor loginValidCheckerInterceptor;
    private final AdminAuthenticationContext adminAuthenticationContext;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        if (AdminAuthenticationExtractor.extractFromRequest(request).isEmpty()) {
            adminAuthenticationContext.setAnonymous();
            return true;
        }

        return loginValidCheckerInterceptor.preHandle(request, response, handler);
    }
}
