package com.atwoz.member.domain.auth;

public interface TokenProvider {

    String createTokenWith(final String phoneNumber);

    String extract(final String token);
}
