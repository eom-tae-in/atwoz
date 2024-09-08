package com.atwoz.interview.application.interview;

import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.application.interview.dto.InterviewUpdateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.exception.exceptions.AlreadyExistedInterviewQuestionException;
import com.atwoz.interview.exception.exceptions.InterviewNotFoundException;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.InterviewCreateRequestFixture.인터뷰_나_질문_요청;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문;
import static com.atwoz.interview.fixture.InterviewUpdateRequestFixture.인터뷰_나_수정_질문_중복_요청;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewServiceTest {

    private InterviewService interviewService;
    private InterviewRepository interviewRepository;

    @BeforeEach
    void init() {
        interviewRepository = new InterviewFakeRepository();
        interviewService = new InterviewService(interviewRepository);
    }

    @Nested
    class 인터뷰_생성 {

        @Test
        void 인터뷰를_생성한다() {
            // given
            Interview expectedInterview = 인터뷰_나_일반_질문();
            InterviewCreateRequest request = 인터뷰_나_질문_요청();

            // when
            Interview interview = interviewService.createInterview(request);

            // then
            assertThat(interview).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(expectedInterview);
        }

        @Test
        void 등록되지_않은_인터뷰_타입이면_예외가_발생한다() {
            // given
            InterviewCreateRequest request = new InterviewCreateRequest("나는 요즘 이런걸 배워보고 싶더라!", "abcd");

            // when & then
            assertThatThrownBy(() -> interviewService.createInterview(request))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }

        @Test
        void 이미_등록한_질문이면_예외가_발생한다() {
            // given
            InterviewCreateRequest request = 인터뷰_나_질문_요청();
            interviewService.createInterview(request);

            // when & then
            assertThatThrownBy(() -> interviewService.createInterview(request))
                    .isInstanceOf(AlreadyExistedInterviewQuestionException.class);
        }
    }

    @Nested
    class 인터뷰_수정 {

        @Test
        void 인터뷰를_수정한다() {
            // given
            Interview interview = interviewService.createInterview(new InterviewCreateRequest("인터뷰 질문", "나"));
            InterviewUpdateRequest request = new InterviewUpdateRequest("타입 변경", "연인");

            // when
            interviewService.updateInterview(interview.getId(), request);

            // then
            assertSoftly(softly -> {
                softly.assertThat(interview.getQuestion()).isEqualTo(request.question());
                softly.assertThat(interview.getInterviewType().getName()).isEqualTo(request.type());
            });
        }

        @Test
        void 등록되지_않은_인터뷰_id면_예외가_발생한다() {
            // given
            interviewService.createInterview(new InterviewCreateRequest("인터뷰 질문", "나"));
            Long id = -1L;
            InterviewUpdateRequest request = new InterviewUpdateRequest("변경 질문", "연인");

            // when & then
            assertThatThrownBy(() -> interviewService.updateInterview(id, request))
                    .isInstanceOf(InterviewNotFoundException.class);
        }

        @Test
        void 등록되지_않은_인터뷰_타입이면_예외가_발생한다() {
            // given
            Interview interview = interviewService.createInterview(인터뷰_나_질문_요청());
            InterviewUpdateRequest request = new InterviewUpdateRequest("변경 질문", "abcd");

            // when & then
            assertThatThrownBy(() -> interviewService.updateInterview(interview.getId(), request))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }

        @Test
        void 이미_등록한_질문이면_예외가_발생한다() {
            // given
            InterviewCreateRequest createRequest = new InterviewCreateRequest("질문", "나");
            InterviewCreateRequest alreadyCreatedRequest = 인터뷰_나_질문_요청();
            Interview interview = interviewService.createInterview(createRequest);
            interviewService.createInterview(alreadyCreatedRequest);

            InterviewUpdateRequest updateRequest = 인터뷰_나_수정_질문_중복_요청();

            // when & then
            assertThatThrownBy(() -> interviewService.updateInterview(interview.getId(), updateRequest))
                    .isInstanceOf(AlreadyExistedInterviewQuestionException.class);
        }
    }
}
