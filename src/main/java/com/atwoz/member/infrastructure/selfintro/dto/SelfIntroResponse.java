package com.atwoz.member.infrastructure.selfintro.dto;

public record SelfIntroResponse(
        Long selfIntroId,
        String selfIntroContent,
        String nickname,
        String city,
        int age,
        int height
) {
}
