package com.atwoz.interview.fixture.interview;

import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;

public class InterviewUpdateRequestFixture {

    public static InterviewUpdateRequest 인터뷰_나_수정_질문_중복_요청() {
        return new InterviewUpdateRequest("나는 요즘 이런걸 배워보고 싶더라!", "나");
    }

    public static InterviewUpdateRequest 인터뷰_수정_질문_요청() {
        return new InterviewUpdateRequest("인터뷰 질문 수정", "연인");
    }
}
