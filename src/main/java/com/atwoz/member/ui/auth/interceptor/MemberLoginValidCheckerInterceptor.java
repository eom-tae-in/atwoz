package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.exception.exceptions.auth.MemberLoginInvalidException;
import com.atwoz.member.ui.auth.support.MemberAuthenticationContext;
import com.atwoz.member.ui.auth.support.MemberAuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class MemberLoginValidCheckerInterceptor implements HandlerInterceptor {

    private static final String MEMBER_ID = "id";

    private final MemberAuthenticationContext memberAuthenticationContext;
    private final MemberAuthenticationExtractor memberAuthenticationExtractor;
    private final MemberTokenProvider memberTokenProvider;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String token = memberAuthenticationExtractor.extractFromRequest(request)
                .orElseThrow(MemberLoginInvalidException::new);
        Long extractedId = memberTokenProvider.extract(token, MEMBER_ID, Long.class);
        memberAuthenticationContext.setAuthentication(extractedId);

        return true;
    }
}
