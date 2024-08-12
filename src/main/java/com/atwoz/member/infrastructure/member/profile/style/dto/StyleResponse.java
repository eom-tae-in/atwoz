package com.atwoz.member.infrastructure.member.profile.style.dto;

import com.atwoz.member.domain.member.profile.Style;
import lombok.Builder;

@Builder
public record StyleResponse(
        String name,
        String code
) {

    public static StyleResponse from(final Style style) {
        return StyleResponse.builder()
                .name(style.getName())
                .code(style.getCode())
                .build();
    }
}
