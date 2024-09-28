package com.atwoz.interview.ui.memberinterview.dto;

import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;

import java.util.List;

public record MemberInterviewsResponse(
        List<MemberInterviewSimpleResponse> interviews
) {
}
