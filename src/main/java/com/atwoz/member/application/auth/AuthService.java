package com.atwoz.member.application.auth;

import com.atwoz.global.event.Events;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.application.auth.event.ValidatedLoginEvent;
import com.atwoz.member.domain.auth.TokenProvider;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private static final String DEFAULT_PHONE_NUMBER = "01011111111";

    private final TokenProvider tokenProvider;
    private final OAuthRequester oAuthRequester;

    @Transactional
    public String login(final LoginRequest request, final OAuthProviderRequest provider) {
        String accessToken = oAuthRequester.getAccessToken(request.code(), provider);
        MemberInfoResponse memberInfoResponse = oAuthRequester.getMemberInfo(accessToken, provider);

        /**
         * OAuth 인증방식과 PASS 인증 방식에 차이가 존재해서 회의 후 메서드 변경을 진행할 예정
         */
        Events.raise(new ValidatedLoginEvent(DEFAULT_PHONE_NUMBER));

        return tokenProvider.createTokenWith(DEFAULT_PHONE_NUMBER);
    }
}
