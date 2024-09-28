package com.atwoz.interview.ui.interview.dto;

import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;

import java.util.List;

public record InterviewsResponse(
        List<InterviewResponse> interviews
) {
}
