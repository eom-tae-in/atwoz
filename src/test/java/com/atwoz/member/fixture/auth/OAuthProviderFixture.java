package com.atwoz.member.fixture.auth;

import com.atwoz.member.infrastructure.auth.dto.MemberInfoKeyWordRequest;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;

@SuppressWarnings("NonAsciiCharacters")
public class OAuthProviderFixture {

    public static OAuthProviderRequest 인증_기관_생성() {

        return new OAuthProviderRequest("clientId",
                "clientSecret",
                "redirectUri",
                "tokenUri",
                "userInfoUri",
                new MemberInfoKeyWordRequest("emailKeyWord", "nicknameKeyWord"));
    }
}
