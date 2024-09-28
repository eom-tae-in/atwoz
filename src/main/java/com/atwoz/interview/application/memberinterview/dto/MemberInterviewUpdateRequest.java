package com.atwoz.interview.application.memberinterview.dto;

import jakarta.validation.constraints.NotEmpty;

public record MemberInterviewUpdateRequest(
        @NotEmpty(message = "수정할 답변이 필요합니다.")
        String answer
) {
}
