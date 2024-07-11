package com.atwoz.admin.domain.admin;

import com.atwoz.admin.domain.admin.service.AdminRefreshTokenProvider;
import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record AdminRefreshToken(
        @Id String refreshToken,
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
