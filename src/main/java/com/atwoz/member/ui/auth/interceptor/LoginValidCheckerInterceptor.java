package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.auth.LoginInvalidException;
import com.atwoz.member.ui.auth.support.auth.AuthenticationContext;
import com.atwoz.member.ui.auth.support.auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class LoginValidCheckerInterceptor implements HandlerInterceptor {

    private final TokenProvider tokenProvider;
    private final AuthenticationContext authenticationContext;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) throws Exception {
        String token = AuthenticationExtractor.extract(request)
                .orElseThrow(LoginInvalidException::new);

        String memberPhoneNumber = tokenProvider.extract(token);
        Member foundMember = memberRepository.findByPhoneNumber(memberPhoneNumber)
                .orElseThrow(RuntimeException::new);
        authenticationContext.setAuthentication(foundMember.getId());

        return true;
    }
}
