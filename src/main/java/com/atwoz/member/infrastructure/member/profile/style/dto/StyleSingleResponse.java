package com.atwoz.member.infrastructure.member.profile.style.dto;

import com.atwoz.member.domain.member.profile.Style;

public record StyleSingleResponse(
        String name,
        String code
) {

    public static StyleSingleResponse from(final Style style) {
        return new StyleSingleResponse(
                style.getName(),
                style.getCode()
        );
    }
}
