package com.atwoz.admin.config;

import com.atwoz.admin.ui.auth.interceptor.AdminLoginValidCheckerInterceptor;
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

import static com.atwoz.global.config.interceptor.support.HttpMethod.OPTIONS;

@RequiredArgsConstructor
@Configuration
public class AdminAuthConfig implements WebMvcConfigurer {

    private final AdminAuthArgumentResolver adminAuthArgumentResolver;
    private final AdminRefreshTokenExtractionArgumentResolver adminRefreshTokenExtractionArgumentResolver;
    private final AdminLoginValidCheckerInterceptor adminLoginValidCheckerInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginValidCheckerInterceptor());
    }

    private HandlerInterceptor adminLoginValidCheckerInterceptor() {
        return new PathMatcherInterceptor(adminLoginValidCheckerInterceptor)
                .excludePathPattern("/**", OPTIONS);
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminAuthArgumentResolver);
        resolvers.add(adminRefreshTokenExtractionArgumentResolver);
    }
}
