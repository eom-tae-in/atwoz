package com.atwoz.interview.infrastructure.memberinterview.dto;

public record MemberInterviewSimpleResponse(
        Long id,
        String question,
        Boolean isSubmitted
) {
}
