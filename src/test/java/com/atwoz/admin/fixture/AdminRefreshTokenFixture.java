package com.atwoz.admin.fixture;

import com.atwoz.admin.domain.admin.AdminRefreshToken;

@SuppressWarnings("NonAsciiCharacters")
public class AdminRefreshTokenFixture {

    private static final String REFRESH_TOKEN = "refreshToken";
    private static final Long DEFAULT_MEMBER_ID = 1L;

    public static AdminRefreshToken 관리자_리프레쉬_토큰_생성() {
        return new AdminRefreshToken(
                REFRESH_TOKEN,
                DEFAULT_MEMBER_ID
        );
    }
}
