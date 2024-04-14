package com.atwoz.member.domain.auth;

public interface TokenProvider {

    String createTokenWithId(Long id);

    String createTokenWithPhoneNumber(String phoneNumber);

    <T> T extract(String token, String claimName, Class<T> classType);
}
