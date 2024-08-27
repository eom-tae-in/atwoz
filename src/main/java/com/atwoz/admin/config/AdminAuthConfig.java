package com.atwoz.admin.config;

import com.atwoz.admin.ui.auth.interceptor.AdminLoginValidCheckerInterceptor;
import com.atwoz.admin.ui.auth.interceptor.ParseAdminIdFromTokenInterceptor;
import com.atwoz.admin.ui.auth.support.resolver.AdminAuthArgumentResolver;
import com.atwoz.admin.ui.auth.support.resolver.AdminRefreshTokenExtractionArgumentResolver;
import com.atwoz.global.config.interceptor.PathMatcherInterceptor;
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
public class AdminAuthConfig implements WebMvcConfigurer {

    private final AdminAuthArgumentResolver adminAuthArgumentResolver;
    private final AdminRefreshTokenExtractionArgumentResolver adminRefreshTokenExtractionArgumentResolver;
    private final ParseAdminIdFromTokenInterceptor parseAdminIdFromTokenInterceptor;
    private final AdminLoginValidCheckerInterceptor adminLoginValidCheckerInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(parseAdminIdFromTokenInterceptor());
        registry.addInterceptor(adminLoginValidCheckerInterceptor());
    }

    private HandlerInterceptor parseAdminIdFromTokenInterceptor() {
        return new PathMatcherInterceptor(parseAdminIdFromTokenInterceptor)
                .excludePathPattern("/**", OPTIONS);
    }

    private HandlerInterceptor adminLoginValidCheckerInterceptor() {
        return new PathMatcherInterceptor(adminLoginValidCheckerInterceptor)
                .excludePathPattern("/**", OPTIONS)
                .addPathPatterns("/api/members/hobbies/**", GET, POST, PATCH, DELETE)
                .addPathPatterns("/api/members/styles/**", GET, POST, PATCH, DELETE);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminAuthArgumentResolver);
        resolvers.add(adminRefreshTokenExtractionArgumentResolver);
    }
}
