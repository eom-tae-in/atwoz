package com.atwoz.interview.ui.interview.dto;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.vo.InterviewType;

public record InterviewUpdateResponse(
        Long id,
        String question,
        String type
) {

    public static InterviewUpdateResponse from(final Interview interview) {
        InterviewType interviewType = interview.getInterviewType();
        return new InterviewUpdateResponse(interview.getId(), interview.getQuestion(), interviewType.getName());
    }
}
