package com.atwoz.member.infrastructure.member.dto;

public record MemberResponse(
        MemberProfileResponse memberProfileResponse,
        HobbiesResponse hobbiesResponse,
        StylesResponse stylesResponse
) {
}
