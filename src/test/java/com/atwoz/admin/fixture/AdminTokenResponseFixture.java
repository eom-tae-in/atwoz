package com.atwoz.admin.fixture;

import com.atwoz.admin.application.auth.dto.AdminAccessTokenResponse;
import com.atwoz.admin.application.auth.dto.AdminTokenResponse;

@SuppressWarnings("NonAsciiCharacters")
public class AdminTokenResponseFixture {

    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";

    public static AdminTokenResponse 관리자_토큰_생성_응답() {
        return new AdminTokenResponse(
                ACCESS_TOKEN,
                REFRESH_TOKEN
        );
    }

    public static AdminAccessTokenResponse 관리자_액세스_토큰_생성_응답() {
        return new AdminAccessTokenResponse(
                ACCESS_TOKEN
        );
    }
}
