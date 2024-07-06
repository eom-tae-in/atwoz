package com.atwoz.admin.application.auth.dto;

public record AdminProfileSignUpRequest(
        String name,
        String phoneNumber
) {
}
