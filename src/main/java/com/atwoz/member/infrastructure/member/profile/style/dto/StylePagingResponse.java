package com.atwoz.member.infrastructure.member.profile.style.dto;

import com.atwoz.member.domain.member.profile.Style;

public record StylePagingResponse(
        Long styleId,
        String name,
        String code
) {

    public static StylePagingResponse from(final Style style) {
        return new StylePagingResponse(
                style.getId(),
                style.getName(),
                style.getCode()
        );
    }
}
