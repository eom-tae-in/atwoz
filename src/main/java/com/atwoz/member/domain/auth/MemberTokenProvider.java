package com.atwoz.member.domain.auth;

// TODO: accessToken, refreshToken 생성하는 주체 변경해야함(ADMIN 처럼)
public interface MemberTokenProvider {

    String createAccessToken(Long id);

    String createRefreshToken(Long id);

    <T> T extract(String token, String claimName, Class<T> classType);
}
