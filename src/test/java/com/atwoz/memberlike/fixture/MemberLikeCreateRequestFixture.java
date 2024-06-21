package com.atwoz.memberlike.fixture;

import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;

public class MemberLikeCreateRequestFixture {

    public static MemberLikeCreateRequest 호감_요청_생성(final Long targetId) {
        return new MemberLikeCreateRequest(targetId, "관심있어요");
    }
}
