package com.atwoz.admin.domain.admin;

import com.atwoz.admin.domain.admin.service.AdminRefreshTokenProvider;
import lombok.Builder;

@Builder
public record AdminRefreshToken(
        String refreshToken,
        Long memberId
) {

    public static AdminRefreshToken createWith(final AdminRefreshTokenProvider adminRefreshTokenProvider,
                                               final String email,
                                               final Long memberId) {
        return AdminRefreshToken.builder()
                .refreshToken(adminRefreshTokenProvider.createRefreshToken(email))
                .memberId(memberId)
                .build();
    }
}
