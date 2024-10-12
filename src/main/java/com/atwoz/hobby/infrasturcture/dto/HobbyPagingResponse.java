package com.atwoz.hobby.infrasturcture.dto;

import com.atwoz.hobby.domain.Hobby;

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
