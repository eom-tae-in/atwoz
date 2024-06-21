package com.atwoz.memberlike.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record MemberLikeCreateRequest(
        @NotNull(message = "대상 회원 id가 필요합니다.")
        Long memberId,

        @NotEmpty(message = "좋아요 수준이 필요합니다.")
        String likeLevel
) {
}
