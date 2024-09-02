package com.atwoz.interview.ui.interview.dto;

import com.atwoz.interview.domain.interview.vo.InterviewType;

public record InterviewCreateResponse(
        String question,
        String type
) {

    public static InterviewCreateResponse of(final String question, final InterviewType type) {
        return new InterviewCreateResponse(question, type.getName());
    }
}
