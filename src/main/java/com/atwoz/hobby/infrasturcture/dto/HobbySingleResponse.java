package com.atwoz.hobby.infrasturcture.dto;

import com.atwoz.hobby.domain.Hobby;

public record HobbySingleResponse(
        String name,
        String code
) {

    public static HobbySingleResponse from(final Hobby hobby) {
        return new HobbySingleResponse(
                hobby.getName(),
                hobby.getCode()
        );
    }
}
