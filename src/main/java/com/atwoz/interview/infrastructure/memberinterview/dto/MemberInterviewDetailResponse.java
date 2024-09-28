package com.atwoz.interview.infrastructure.memberinterview.dto;

public record MemberInterviewDetailResponse(
        Long id,
        String question,
        String answer
) {
}
