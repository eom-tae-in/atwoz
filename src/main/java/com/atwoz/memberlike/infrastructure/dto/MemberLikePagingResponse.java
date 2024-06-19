package com.atwoz.memberlike.infrastructure.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record MemberLikePagingResponse(
        List<MemberLikeSimpleResponse> memberLikes,
        int nextPage
) {

    public static MemberLikePagingResponse of(final Page<MemberLikeSimpleResponse> memberLikes, final int nextPage) {
        return new MemberLikePagingResponse(memberLikes.getContent(), nextPage);
    }
}
