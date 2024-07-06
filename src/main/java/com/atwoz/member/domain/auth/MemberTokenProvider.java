package com.atwoz.member.domain.auth;

public interface MemberTokenProvider {

    String createAccessToken(Long id);

    String createRefreshToken(Long id);

    <T> T extract(String token, String claimName, Class<T> classType);
}
