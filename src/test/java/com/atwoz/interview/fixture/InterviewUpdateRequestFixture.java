package com.atwoz.interview.fixture;

import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;

public class InterviewUpdateRequestFixture {

    public static InterviewUpdateRequest 인터뷰_나_수정_질문_중복_요청() {
        return new InterviewUpdateRequest("나는 요즘 이런걸 배워보고 싶더라!", "나");
    }
}
