package com.atwoz.memberlike.infrastructure.dto;

public record MemberLikeSimpleResponse(
        Long memberId,
        String nickname,
        String city,
        String sector,
        Integer age,
        String LikeIcon,
        Boolean isRecent
) {
}
