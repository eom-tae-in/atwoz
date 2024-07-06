package com.atwoz.admin.ui.auth.interceptor;

import com.atwoz.admin.domain.admin.AdminTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import com.atwoz.admin.exception.exceptions.UnauthorizedAccessToAdminException;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationContext;
import com.atwoz.admin.ui.auth.support.AdminAuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class AdminLoginValidCheckerInterceptor implements HandlerInterceptor {

    private static final String ADMIN_ID = "id";
    private static final String ROLE = "role";
    private static final String ADMIN = "admin";

    private final AdminAuthenticationContext adminAuthenticationContext;
    private final AdminAuthenticationExtractor adminAuthenticationExtractor;
    private final AdminTokenProvider adminTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String token = adminAuthenticationExtractor.extractFromRequest(request)
                .orElseThrow(AdminLoginInvalidException::new);
        String extractedRole = adminTokenProvider.extract(token, ROLE, String.class);
        if (!extractedRole.equals(ADMIN)) {
            throw new UnauthorizedAccessToAdminException();
        }

        Long extractedId = adminTokenProvider.extract(token, ADMIN_ID, Long.class);
        adminAuthenticationContext.setAuthentication(extractedId);

        return true;
    }
}
