package com.atwoz.interview.application.interview;

import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_타입_질문;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewQueryServiceTest {

    private InterviewQueryService interviewQueryService;
    private InterviewRepository interviewRepository;

    @BeforeEach
    void init() {
        interviewRepository = new InterviewFakeRepository();
        interviewQueryService = new InterviewQueryService(interviewRepository);
    }

    @Nested
    class 인터뷰_목록_타입_조회 {

        @Test
        void 인터뷰_목록을_타입으로_조회한다() {
            // given
            String type = "나";
            saveOtherInterviews();

            // when
            List<InterviewResponse> interviews = interviewQueryService.findInterviewsByType(type);

            // then
            assertSoftly(softly -> {
                softly.assertThat(interviews.size()).isEqualTo(3);
                softly.assertThat(interviews).extracting(InterviewResponse::question)
                        .containsExactlyInAnyOrder(
                                "내가 생각하는 내 장점과 단점은 이거다!",
                                "내가 생각하는 나의 반전 매력은 이거야!",
                                "10년 뒤 멋진 내 모습, 과연 어떤 모습일까?"
                        );
                softly.assertThat(interviews).extracting(InterviewResponse::type)
                        .containsOnly(InterviewType.ME);
            });
        }

        @Test
        void 없는_인터뷰_타입이면_예외가_발생한다() {
            // given
            String type = "abc";
            saveOtherInterviews();

            // when & then
            assertThatThrownBy(() -> interviewQueryService.findInterviewsByType(type))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }
    }

    private void saveOtherInterviews() {
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 나의 반전 매력은 이거야!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "10년 뒤 멋진 내 모습, 과연 어떤 모습일까?"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "남사친/여사친에 대한 나의 생각"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "나의 인간관계 스타일"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "호감을 느끼는 사람의 유형/타입"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "추구하는 만남 횟수와 연락 빈도"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "내가 연인을 사랑하는 방식"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인에게 바라는 점"));
    }
}
