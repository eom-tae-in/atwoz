package com.atwoz.interview.application.memberinterview;

import com.atwoz.interview.application.memberinterview.dto.MemberInterviewSubmitRequest;
import com.atwoz.interview.application.memberinterview.dto.MemberInterviewUpdateRequest;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.domain.memberinterview.MemberInterview;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
import com.atwoz.interview.exception.exceptions.MemberInterviewNotFoundException;
import com.atwoz.interview.exception.exceptions.MemberInterviewNotSubmittedException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import com.atwoz.interview.infrastructure.memberinterview.MemberInterviewsFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_타입_질문;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberInterviewsServiceTest {

    private MemberInterviewsService memberInterviewsService;
    private MemberInterviewsRepository memberInterviewsRepository;
    private InterviewRepository interviewRepository;

    @BeforeEach
    void init() {
        interviewRepository = new InterviewFakeRepository();
        memberInterviewsRepository = new MemberInterviewsFakeRepository(interviewRepository);
        memberInterviewsService = new MemberInterviewsService(interviewRepository, memberInterviewsRepository);
    }

    @Nested
    class 회원_인터뷰_등록 {

        @Test
        void 회원_인터뷰를_등록한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "질문"));
            MemberInterviewSubmitRequest request = new MemberInterviewSubmitRequest("답변");

            // when
            memberInterviewsService.submitInterview(interviewId, memberId, request);

            // then
            Optional<MemberInterviews> memberInterviews = memberInterviewsRepository.findByMemberId(memberId);
            assertSoftly(softly -> {
                softly.assertThat(memberInterviews).isPresent();
                softly.assertThat(memberInterviews.get().getMemberInterviews()).isNotEmpty();
            });
        }

        @Test
        void 없는_인터뷰_id로_등록하면_예외가_발생한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            MemberInterviewSubmitRequest request = new MemberInterviewSubmitRequest("답변");

            // when & then
            assertThatThrownBy(() -> memberInterviewsService.submitInterview(interviewId, memberId, request))
                    .isInstanceOf(InterviewNotFoundException.class);
        }
    }

    @Nested
    class 회원_인터뷰_수정 {

        @Test
        void 회원_인터뷰를_수정한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            String beforeAnswer = "이전 답변";
            String afterAnswer = "이후 답변";
            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "질문"));
            MemberInterviewSubmitRequest submitRequest = new MemberInterviewSubmitRequest(beforeAnswer);
            memberInterviewsService.submitInterview(interviewId, memberId, submitRequest);

            MemberInterviewUpdateRequest request = new MemberInterviewUpdateRequest(afterAnswer);

            // when
            MemberInterview memberInterview = memberInterviewsService.updateMemberInterviewAnswer(interviewId, memberId, request);

            // then
            assertSoftly(softly -> {
                softly.assertThat(memberInterview.getAnswer()).isNotEqualTo(beforeAnswer);
                softly.assertThat(memberInterview.getAnswer()).isEqualTo(afterAnswer);
            });
        }

        @Test
        void 회원_인터뷰_목록이_초기화되지_않았을_때_수정하면_예외가_발생한다() {
            // given
            Long interviewId = 1L;
            Long memberId = 1L;
            String answer = "답변";

            MemberInterviewUpdateRequest request = new MemberInterviewUpdateRequest(answer);

            // when & then
            assertThatThrownBy(() -> memberInterviewsService.updateMemberInterviewAnswer(interviewId, memberId, request))
                    .isInstanceOf(MemberInterviewNotSubmittedException.class);
        }

        @Test
        void 없는_인터뷰_id로_수정하면_예외가_발생한다() {
            // given
            Long wrongInterviewId = -1L;
            Long interviewId = 1L;
            Long memberId = 1L;
            String answer = "답변";

            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "질문 1"));
            MemberInterviewSubmitRequest submitRequest = new MemberInterviewSubmitRequest(answer);
            MemberInterviewUpdateRequest request = new MemberInterviewUpdateRequest(answer);

            memberInterviewsService.submitInterview(interviewId, memberId, submitRequest);

            // when & then
            assertThatThrownBy(() -> memberInterviewsService.updateMemberInterviewAnswer(wrongInterviewId, memberId, request))
                    .isInstanceOf(MemberInterviewNotFoundException.class);
        }

        @Test
        void 등록하지_않은_인터뷰를_수정하면_예외가_발생한다() {
            // given
            Long interviewId = 1L;
            Long otherInterviewId = 2L;
            Long memberId = 1L;
            String answer = "답변";
            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "질문 1"));
            interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "질문 2"));
            MemberInterviewSubmitRequest submitRequest = new MemberInterviewSubmitRequest(answer);
            memberInterviewsService.submitInterview(interviewId, memberId, submitRequest);

            MemberInterviewUpdateRequest request = new MemberInterviewUpdateRequest(answer);

            // when & then
            assertThatThrownBy(() -> memberInterviewsService.updateMemberInterviewAnswer(otherInterviewId, memberId, request))
                    .isInstanceOf(MemberInterviewNotFoundException.class);
        }
    }
}
