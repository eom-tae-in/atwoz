package com.atwoz.interview.application.interview.dto;

import jakarta.validation.constraints.NotEmpty;

public record InterviewCreateRequest(
        @NotEmpty(message = "인터뷰 질문 내용이 필요합니다.")
        String question,

        @NotEmpty(message = "인터뷰 타입이 필요합니다.")
        String type
) {
}
