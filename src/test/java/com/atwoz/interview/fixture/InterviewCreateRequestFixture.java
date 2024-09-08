package com.atwoz.interview.fixture;

import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;

public class InterviewCreateRequestFixture {

    public static InterviewCreateRequest 인터뷰_나_질문_요청() {
        return new InterviewCreateRequest("나는 요즘 이런걸 배워보고 싶더라!", "나");
    }
}
