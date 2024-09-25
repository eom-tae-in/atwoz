package com.atwoz.member.infrastructure.member.profile.hobby.dto;

import com.atwoz.member.domain.member.profile.Hobby;

public record HobbyPagingResponse(
        Long hobbyId,
        String name,
        String code
) {

    public static HobbyPagingResponse from(final Hobby hobby) {
        return new HobbyPagingResponse(
                hobby.getId(),
                hobby.getName(),
                hobby.getCode()
        );
    }
}
