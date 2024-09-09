package com.atwoz.interview.ui.memberinterview.dto;

import java.util.List;

public record MemberInterviewsResponse(
        List<MemberInterviewResponse> interviews
) {
}
