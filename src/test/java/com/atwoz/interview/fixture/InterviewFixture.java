package com.atwoz.interview.fixture;

import com.atwoz.interview.domain.interview.Interview;

@SuppressWarnings("NonAsciiCharacters")
public class InterviewFixture {

    public static Interview 인터뷰_나_일반_질문() {
        return Interview.createWith("나는 요즘 이런걸 배워보고 싶더라!", "나");
    }

    public static Interview 인터뷰_나_질문(final String question) {
        return Interview.createWith(question, "나");
    }
}
