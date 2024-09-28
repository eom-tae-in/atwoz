package com.atwoz.interview.infrastructure.interview;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.interview.domain.interview.InterviewRepository;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_타입_질문;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private InterviewQueryRepository interviewQueryRepository;

    @Autowired
    private InterviewRepository interviewRepository;

    @Test
    void 인터뷰_목록을_타입으로_조회한다() {
        // given
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
        List<InterviewResponse> interviews = interviewQueryRepository.findByInterviewType(InterviewType.ME);

        // then
        assertSoftly(softly -> {
            softly.assertThat(interviews.size()).isEqualTo(3);
            softly.assertThat(interviews).extracting(InterviewResponse::question)
                    .containsExactly(
                            "내가 생각하는 내 장점과 단점은 이거다!",
                            "내가 생각하는 나의 반전 매력은 이거야!",
                            "10년 뒤 멋진 내 모습, 과연 어떤 모습일까?"
                    );
            softly.assertThat(interviews).extracting(InterviewResponse::type)
                    .containsOnly(InterviewType.ME);
        });
    }
}
