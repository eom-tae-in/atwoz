package com.atwoz.admin.ui.auth.support.resolver;

import com.atwoz.admin.ui.auth.support.AdminAuthenticationContext;
import com.atwoz.admin.ui.auth.support.AuthAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AdminAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final AdminAuthenticationContext adminAuthenticationContext;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthAdmin.class) &&
                parameter.getParameterType().equals(Long.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) throws Exception {
        return adminAuthenticationContext.getPrincipal();
    }
}
