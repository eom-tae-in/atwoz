package com.atwoz.interview.domain.memberinterview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.fixture.InterviewFixture;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문_id;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewTest {

    @Nested
    class 회원_인터뷰_생성 {

        @Test
        void 회원_인터뷰_정상_생성() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            String answer = "답변";

            // when
            MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);

            // then
            assertSoftly(softly -> {
                softly.assertThat(memberInterview.getInterview()).isEqualTo(interview);
                softly.assertThat(memberInterview.getAnswer()).isEqualTo(answer);
            });
        }
    }

    @Nested
    class 회원_인터뷰_수정 {

        @Test
        void 답변을_수정한다() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            String beforeAnswer = "이전 답변";
            String afterAnswer = "이후 답변";
            MemberInterview memberInterview = MemberInterview.createDefault(interview, beforeAnswer);

            // when
            memberInterview.updateAnswer(afterAnswer);

            // then
            assertSoftly(softly -> {
                softly.assertThat(memberInterview.getAnswer()).isNotEqualTo(beforeAnswer);
                softly.assertThat(memberInterview.getAnswer()).isEqualTo(afterAnswer);
            });
        }
    }

    @Nested
    class 회원_인터뷰_조회 {

        @Test
        void 가진_인터뷰_id를_조회한다() {
            // given
            Interview interview = 인터뷰_나_일반_질문_id(1L);
            String answer = "답변";
            MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);

            // when
            Long interviewId = memberInterview.getInterviewId();

            // then
            assertThat(interviewId).isEqualTo(1L);
        }
    }

    @Nested
    class 회원_인터뷰_비교 {

        @Test
        void 가진_인터뷰가_같은지_비교한다() {
            // given
            Long interviewId = 1L;
            Interview interview = InterviewFixture.인터뷰_나_일반_질문_id(interviewId);
            String answer = "답변";
            MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);

            // when
            boolean same = memberInterview.isSameInterview(interviewId);

            // then
            assertThat(same).isTrue();
        }

        @Test
        void 가진_인터뷰_타입이_같은지_비교한다() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            InterviewType interviewType = InterviewType.ME;
            String answer = "답변";
            MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);

            // when
            boolean same = memberInterview.isSameType(interviewType);

            // then
            assertThat(same).isTrue();
        }
    }
}
