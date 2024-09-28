package com.atwoz.interview.fixture;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.vo.InterviewType;

@SuppressWarnings("NonAsciiCharacters")
public class InterviewFixture {

    public static Interview 인터뷰_나_일반_질문() {
        return Interview.createWith("나는 요즘 이런걸 배워보고 싶더라!", "나");
    }

    public static Interview 인터뷰_나_질문(final String question) {
        return Interview.createWith(question, "나");
    }

    public static Interview 인터뷰_나_일반_질문_id(final Long id) {
        return Interview.builder()
                .id(id)
                .interviewType(InterviewType.ME)
                .question("나는 요즘 이런걸 배워보고 싶더라!")
                .build();
    }

    public static Interview 인터뷰_타입_질문(final InterviewType type, final String question) {
        return Interview.builder()
                .interviewType(type)
                .question(question)
                .build();
    }
}
