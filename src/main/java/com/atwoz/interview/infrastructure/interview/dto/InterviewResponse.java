package com.atwoz.interview.infrastructure.interview.dto;

import com.atwoz.interview.domain.interview.vo.InterviewType;

public record InterviewResponse(
        Long id,
        String question,
        InterviewType type
) {
}
