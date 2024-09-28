package com.atwoz.interview.domain.memberinterview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.exception.exceptions.MemberInterviewNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문_id;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewsTest {

    @Nested
    class 회원_인터뷰_목록_생성 {

        @Test
        void 회원_인터뷰_목록_정상_생성() {
            // given
            Long memberId = 1L;

            // when
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(memberInterviews.getMemberId()).isEqualTo(memberId);
                softly.assertThat(memberInterviews.getMemberInterviews()).isEmpty();
            });
        }
    }

    @Nested
    class 회원_인터뷰_목록_제어 {

        @Test
        void 회원_인터뷰_목록에_새로운_회원_인터뷰를_등록한다() {
            // given
            Long memberId = 1L;
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
            Interview interview = 인터뷰_나_일반_질문();
            String answer = "답변";
            MemberInterview memberInterview = MemberInterview.createDefault(interview, answer);

            // when
            memberInterviews.submitInterview(interview, answer);

            // then
            assertThat(memberInterviews.getMemberInterviews()).contains(memberInterview);
        }

        @Test
        void 특정_회원_인터뷰를_수정한다() {
            // given
            Long memberId = 1L;
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
            Interview interview = 인터뷰_나_일반_질문_id(1L);
            String beforeAnswer = "이전 답변";
            String afterAnswer = "이후 답변";
            memberInterviews.submitInterview(interview, beforeAnswer);

            // when
            memberInterviews.updateInterviewAnswer(1L, afterAnswer);

            // then
            MemberInterview memberInterview = memberInterviews.getMemberInterviews().get(0);
            assertThat(memberInterview.getAnswer()).isEqualTo(afterAnswer);
        }

        @Test
        void 회원_인터뷰를_찾지_못할_경우_수정_시_예외가_발생한다() {
            // given
            Long memberId = 1L;
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
            String afterAnswer = "이후 답변";

            // when & then
            assertThatThrownBy(() -> memberInterviews.updateInterviewAnswer(1L, afterAnswer))
                    .isInstanceOf(MemberInterviewNotFoundException.class);
        }

        @Test
        void 특정_회원_인터뷰를_조회한다() {
            // given
            Long memberId = 1L;
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);
            Interview interview = 인터뷰_나_일반_질문_id(1L);
            String answer = "답변";
            memberInterviews.submitInterview(interview, answer);

            // when
            MemberInterview memberInterview = memberInterviews.findMemberInterview(1L);

            // then
            assertSoftly(softly -> {
                softly.assertThat(memberInterview.getInterviewId()).isEqualTo(1L);
                softly.assertThat(memberInterview.getAnswer()).isEqualTo(answer);
            });
        }

        @Test
        void 회원_인터뷰를_찾지_못할_경우_조회_시_예외가_발생한다() {
            // given
            Long memberId = 1L;
            MemberInterviews memberInterviews = MemberInterviews.createWithMemberId(memberId);

            // when & then
            assertThatThrownBy(() -> memberInterviews.findMemberInterview(1L))
                    .isInstanceOf(MemberInterviewNotFoundException.class);
        }
    }
}
