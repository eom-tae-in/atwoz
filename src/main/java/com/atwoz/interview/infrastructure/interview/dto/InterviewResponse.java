package com.atwoz.interview.infrastructure.interview.dto;

public record InterviewResponse(
        Long id,
        String question,
        String type
) {
}
