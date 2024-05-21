package com.atwoz.member.config;

import com.atwoz.member.ui.auth.interceptor.LoginValidCheckerInterceptor;
import com.atwoz.member.ui.auth.interceptor.ParseMemberIdFromTokenInterceptor;
import com.atwoz.member.ui.auth.interceptor.PathMatcherInterceptor;
import com.atwoz.member.ui.auth.interceptor.TokenRegenerateInterceptor;
import com.atwoz.member.ui.auth.support.resolver.AuthArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import static com.atwoz.member.ui.auth.interceptor.HttpMethod.DELETE;
import static com.atwoz.member.ui.auth.interceptor.HttpMethod.GET;
import static com.atwoz.member.ui.auth.interceptor.HttpMethod.OPTIONS;
import static com.atwoz.member.ui.auth.interceptor.HttpMethod.PATCH;
import static com.atwoz.member.ui.auth.interceptor.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
public class AuthConfig implements WebMvcConfigurer {

    private final AuthArgumentResolver authArgumentResolver;
    private final ParseMemberIdFromTokenInterceptor parseMemberIdFromTokenInterceptor;
    private final LoginValidCheckerInterceptor loginValidCheckerInterceptor;
    private final TokenRegenerateInterceptor tokenRegenerateInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(parseMemberIdFromTokenInterceptor());
        registry.addInterceptor(loginValidCheckerInterceptor());
        registry.addInterceptor(tokenRegenerateInterceptor());
    }

    private HandlerInterceptor parseMemberIdFromTokenInterceptor() {
        return new PathMatcherInterceptor(parseMemberIdFromTokenInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .addPathPatterns("/api/info/**", GET, POST, PATCH)
                .addPathPatterns("/api/surveys/**", GET, POST)
                .addPathPatterns("/api/members/me/missions/**", GET, POST, PATCH)
                .addPathPatterns("/api/members/me/surveys/**", GET, POST);
    }

    /**
     * @AuthMember를 통해서 인증이 필요한 경우에 해당 메서드에 URI를 추가해주면 된다. 추가를 해야지 인증,인가 가능
     */
    private HandlerInterceptor loginValidCheckerInterceptor() {
        return new PathMatcherInterceptor(loginValidCheckerInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .excludePathPattern("/api/missions/**", GET, POST, PATCH, DELETE)
                .excludePathPattern("/api/surveys/**", GET, POST)
                .addPathPatterns("/api/members/**", GET, POST, PATCH, DELETE);
    }

    private HandlerInterceptor tokenRegenerateInterceptor() {
        return new PathMatcherInterceptor(tokenRegenerateInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .addPathPatterns("/api/auth/login", POST);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authArgumentResolver);
    }
}
