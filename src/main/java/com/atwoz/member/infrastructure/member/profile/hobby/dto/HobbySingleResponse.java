package com.atwoz.member.infrastructure.member.profile.hobby.dto;

import com.atwoz.member.domain.member.profile.Hobby;

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
