package com.atwoz.interview.application.memberinterview.dto;

import jakarta.validation.constraints.NotEmpty;

public record MemberInterviewSubmitRequest(
        @NotEmpty(message = "인터뷰 답변이 있어야 합니다.")
        String answer
) {
}
