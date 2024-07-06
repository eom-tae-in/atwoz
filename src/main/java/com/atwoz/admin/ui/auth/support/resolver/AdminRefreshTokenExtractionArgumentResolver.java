package com.atwoz.admin.ui.auth.support.resolver;

import com.atwoz.admin.domain.admin.AdminTokenProvider;
import com.atwoz.admin.exception.exceptions.AdminLoginInvalidException;
import com.atwoz.admin.exception.exceptions.UnauthorizedAccessToAdminException;
import com.atwoz.admin.ui.auth.support.AdminRefreshToken;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AdminRefreshTokenExtractionArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String ROLE = "role";
    private static final String ADMIN = "admin";

    private final AdminTokenProvider adminTokenProvider;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AdminRefreshToken.class) &&
                parameter.getParameterType().equals(String.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null) {
            throw new AdminLoginInvalidException();
        }

        String refreshToken = findRefreshToken(request.getCookies());
        String role = adminTokenProvider.extract(refreshToken, ROLE, String.class);
        if (!Objects.equals(role, ADMIN)) {
            throw new UnauthorizedAccessToAdminException();
        }

        return refreshToken;
    }

    private String findRefreshToken(final Cookie[] cookies) {
        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
                .findAny()
                .orElseThrow(AdminLoginInvalidException::new)
                .getValue();
    }
}
