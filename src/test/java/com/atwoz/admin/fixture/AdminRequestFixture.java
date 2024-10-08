package com.atwoz.admin.fixture;

import com.atwoz.admin.application.auth.dto.AdminLoginRequest;
import com.atwoz.admin.application.auth.dto.AdminSignUpRequest;

public class AdminRequestFixture {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "010-1234-5678";

    public static AdminSignUpRequest 관리자_회원_가입_요청() {
        return new AdminSignUpRequest(
                EMAIL,
                PASSWORD,
                PASSWORD,
                NAME,
                PHONE_NUMBER
        );
    }

    public static AdminLoginRequest 관리자_로그인_요청() {
        return new AdminLoginRequest(
                EMAIL,
                PASSWORD
        );
    }

    public static AdminLoginRequest 관리자_로그인_요청(final String email,
                                               final String password) {
        return new AdminLoginRequest(
                email,
                password
        );
    }
}
