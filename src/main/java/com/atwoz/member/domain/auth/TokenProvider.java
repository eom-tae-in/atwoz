package com.atwoz.member.domain.auth;

public interface TokenProvider {

    String createTokenWith(String phoneNumber);

    String extract(String token);
}
