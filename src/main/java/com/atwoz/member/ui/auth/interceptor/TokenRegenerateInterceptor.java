package com.atwoz.member.ui.auth.interceptor;

import com.atwoz.member.domain.auth.JsonMapper;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import com.atwoz.member.ui.auth.dto.TokenResponse;
import com.atwoz.member.ui.auth.support.auth.AuthenticationExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Component
public class TokenRegenerateInterceptor implements HandlerInterceptor {

    private static final String PHONE_NUMBER = "phoneNumber";

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;
    private final JsonMapper jsonMapper;
    private final AuthenticationExtractor authenticationExtractor;

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response,
                           final Object handler,
                           final @Nullable ModelAndView modelAndView) throws Exception {
        String token = authenticationExtractor.extractFromResponse(response);
        String extractedPhoneNumber = tokenProvider.extract(token, PHONE_NUMBER, String.class);

        if (extractedPhoneNumber != null) {
            response.resetBuffer();
            Member foundMember = findMemberByPhoneNumber(extractedPhoneNumber);
            String createdToken = tokenProvider.createTokenWithId(foundMember.getId());
            byte[] convertedTokenResponse = jsonMapper.convertObjectToJsonByteArray(new TokenResponse(createdToken));
            response.getOutputStream().write(convertedTokenResponse);
        }
    }

    private Member findMemberByPhoneNumber(final String phoneNumber) {
        return memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(MemberNotFoundException::new);
    }
}
