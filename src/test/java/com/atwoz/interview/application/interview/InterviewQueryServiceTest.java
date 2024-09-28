package com.atwoz.interview.application.interview;

import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import com.atwoz.interview.infrastructure.interview.InterviewFakeRepository;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_타입_질문;
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

    @Test
    void 인터뷰_목록을_타입으로_조회한다() {
        // given
        String type = "나";

        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 나의 반전 매력은 이거야!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "10년 뒤 멋진 내 모습, 과연 어떤 모습일까?"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 1"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 2"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 3"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 1"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 2"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 3"));

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
                    .containsOnly(type);
        });
    }

    @Test
    void 없는_인터뷰_타입이면_예외가_발생한다() {
        // given
        String type = "abc";

        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 내 장점과 단점은 이거다!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "내가 생각하는 나의 반전 매력은 이거야!"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.ME, "10년 뒤 멋진 내 모습, 과연 어떤 모습일까?"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 1"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 2"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.RELATIONSHIP, "관계 관련 질문 3"));

        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 1"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 2"));
        interviewRepository.save(인터뷰_타입_질문(InterviewType.COUPLE, "연인 관련 질문 3"));

        // when & then
        assertThatThrownBy(() -> interviewQueryService.findInterviewsByType(type))
                .isInstanceOf(InterviewTypeNotFoundException.class);
    }
}
