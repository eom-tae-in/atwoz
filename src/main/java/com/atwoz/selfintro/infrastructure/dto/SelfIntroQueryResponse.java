package com.atwoz.selfintro.infrastructure.dto;

public record SelfIntroQueryResponse(
        Long selfIntroId,
        String selfIntroContent,
        String nickname,
        String city,
        int age,
        int height
) {
}
