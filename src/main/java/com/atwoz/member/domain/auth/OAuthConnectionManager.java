package com.atwoz.member.domain.auth;

import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;

public interface OAuthConnectionManager {

    String getAccessTokenResponse (OAuthProviderRequest oAuthProviderRequest, String code);

    String getMemberInfoResponse(String accessToken, String userInfoUrl);
}
