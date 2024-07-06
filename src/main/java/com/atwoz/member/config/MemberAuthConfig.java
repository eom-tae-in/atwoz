package com.atwoz.member.config;

import com.atwoz.global.config.interceptor.PathMatcherInterceptor;
import com.atwoz.member.ui.auth.interceptor.MemberLoginValidCheckerInterceptor;
import com.atwoz.member.ui.auth.support.resolver.MemberAuthArgumentResolver;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.atwoz.global.config.interceptor.support.HttpMethod.DELETE;
import static com.atwoz.global.config.interceptor.support.HttpMethod.GET;
import static com.atwoz.global.config.interceptor.support.HttpMethod.OPTIONS;
import static com.atwoz.global.config.interceptor.support.HttpMethod.PATCH;
import static com.atwoz.global.config.interceptor.support.HttpMethod.POST;

@RequiredArgsConstructor
@Configuration
public class MemberAuthConfig implements WebMvcConfigurer {

    private final MemberAuthArgumentResolver memberAuthArgumentResolver;
    private final MemberLoginValidCheckerInterceptor memberLoginValidCheckerInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(loginValidCheckerInterceptor());
    }

    /**
     * @AuthMember를 통해서 인증이 필요한 경우에 해당 메서드에 URI를 추가해주면 된다. 추가를 해야지 인증,인가 가능
     */
    private HandlerInterceptor loginValidCheckerInterceptor() {
        return new PathMatcherInterceptor(memberLoginValidCheckerInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .excludePathPattern("/api/missions/**", GET, POST, PATCH, DELETE)
                .excludePathPattern("/api/surveys/**", GET, POST)
                .addPathPatterns("/api/members/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/reports/**", POST)
                .addPathPatterns("/api/surveys/**", GET, POST)
                .addPathPatterns("/api/members/me/missions/**", GET, POST, PATCH)
                .addPathPatterns("/api/members/me/surveys/**", GET, POST);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberAuthArgumentResolver);
    }
}
