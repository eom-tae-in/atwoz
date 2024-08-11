package com.atwoz.member.infrastructure.member.profile.hobby.dto;

import com.atwoz.member.domain.member.profile.Hobby;
import lombok.Builder;

@Builder
public record HobbyResponse(
        String name,
        String code
) {

    public static HobbyResponse from(final Hobby hobby) {
        return HobbyResponse.builder()
                .name(hobby.getName())
                .code(hobby.getCode())
                .build();
    }
}
