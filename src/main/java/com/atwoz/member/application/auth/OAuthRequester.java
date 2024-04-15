package com.atwoz.member.application.auth;

import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;

public interface OAuthRequester {

    String getAccessToken(String code, OAuthProviderRequest provider);

    MemberInfoResponse getMemberInfo(String accessToken, OAuthProviderRequest oAuthProviderRequest);
}
