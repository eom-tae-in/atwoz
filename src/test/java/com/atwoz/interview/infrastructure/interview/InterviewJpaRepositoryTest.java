package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.Interview;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import static com.atwoz.interview.fixture.interview.InterviewFixture.인터뷰_나_일반_질문;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class InterviewJpaRepositoryTest {

    @Autowired
    private InterviewJpaRepository interviewJpaRepository;

    @Nested
    class 인터뷰_정상 {

        @Test
        void 인터뷰_생성() {
            // given
            Interview interview = 인터뷰_나_일반_질문();

            // when
            Interview savedInterview = interviewJpaRepository.save(interview);

            // then
            assertThat(interview).usingRecursiveComparison()
                    .isEqualTo(savedInterview);
        }

        @Test
        void 인터뷰_id_조회() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            interviewJpaRepository.save(interview);

            // when
            Optional<Interview> findInterview = interviewJpaRepository.findById(interview.getId());

            // then
            assertSoftly(softly -> {
                softly.assertThat(findInterview).isPresent();
                softly.assertThat(interview).usingRecursiveComparison()
                        .isEqualTo(findInterview.get());
            });
        }

        @Test
        void 인터뷰_질문_존재여부_확인() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            String question = interview.getQuestion();
            interviewJpaRepository.save(interview);

            // when
            boolean existence = interviewJpaRepository.existsByQuestion(question);

            // then
            assertThat(existence).isTrue();
        }

        @Test
        void 인터뷰_질문_수정_시_조회_결과가_달라진다() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            interviewJpaRepository.save(interview);
            String question = interview.getQuestion();

            // when
            interview.updateInterview("변경 질문", "연인");

            // then
            assertThat(interviewJpaRepository.existsByQuestion(question)).isFalse();
        }
    }

    @Nested
    class 인터뷰_예외 {

        @Test
        void 같은_질문을_등록_시_예외가_발생한다() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            interviewJpaRepository.save(interview);

            // when & then
            assertThatThrownBy(() -> interviewJpaRepository.save(인터뷰_나_일반_질문()))
                    .isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}
