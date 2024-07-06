package com.atwoz.member.ui.auth.support.resolver;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.ui.auth.support.OAuthAuthority;
import com.atwoz.member.ui.auth.support.OAuthProperties;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.ContentCachingRequestWrapper;

@RequiredArgsConstructor
@Component
public class OAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String KEY = "provider";

    private final OAuthProperties oAuthProperties;
    private final JsonMapper jsonMapper;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OAuthAuthority.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter,
                                  final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest,
                                  final WebDataBinderFactory binderFactory) {

        ContentCachingRequestWrapper request = webRequest.getNativeRequest(ContentCachingRequestWrapper.class);
        String requestBody = Objects.requireNonNull(request).getContentAsString();
        String provider = jsonMapper.getValueByKey(requestBody, KEY);

        return oAuthProperties.findByProviderName(provider);
    }
}

