package com.atwoz.interview.ui.memberinterview.dto;

public record MemberInterviewResponse(
        Long id,
        String question,
        Boolean isSubmitted
) {
}
