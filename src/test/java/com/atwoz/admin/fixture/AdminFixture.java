package com.atwoz.admin.fixture;

import com.atwoz.admin.domain.admin.Admin;

public class AdminFixture {

    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "name";
    private static final String PHONE_NUMBER = "010-1234-5678";

    public static Admin 관리자_생성() {
        return Admin.createWith(
                EMAIL,
                PASSWORD,
                PASSWORD,
                NAME,
                PHONE_NUMBER
        );
    }

    public static Admin 관리자_생성(final String email,
                               final String password,
                               final String confirmPassword,
                               final String name,
                               final String phoneNumber) {
        return Admin.createWith(
                email,
                password,
                confirmPassword,
                name,
                phoneNumber
        );
    }
}
