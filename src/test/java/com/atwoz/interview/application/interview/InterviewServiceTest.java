package com.atwoz.interview.application.interview;

import com.atwoz.interview.application.interview.dto.InterviewCreateRequest;
import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
            InterviewCreateRequest request = new InterviewCreateRequest("나는 요즘 이런걸 배워보고 싶더라!", "나");

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
    }
}
