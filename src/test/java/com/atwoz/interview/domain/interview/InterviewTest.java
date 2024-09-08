package com.atwoz.interview.domain.interview;

import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.interview.fixture.InterviewFixture.인터뷰_나_일반_질문;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewTest {

    @Nested
    class 인터뷰_생성 {

        @Test
        void 인터뷰_정상_생성() {
            // given
            String question = "인터뷰 질문";
            String type = "연인";

            // when
            Interview interview = Interview.createWith(question, type);

            // then
            assertSoftly(softly -> {
                softly.assertThat(interview.getQuestion()).isEqualTo(question);
                softly.assertThat(interview.getInterviewType().getName()).isEqualTo(type);
            });
        }

        @Test
        void 없는_인터뷰_타입으로_생성_시_예외가_발생한다() {
            // given
            String question = "인터뷰 질문";
            String type = "abcd";

            // when & then
            assertThatThrownBy(() -> Interview.createWith(question, type))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }
    }

    @Nested
    class 인터뷰_수정 {

        @Test
        void 인터뷰_정상_수정() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            String question = "변경_질문";
            String type = "연인";

            // when
            interview.updateInterview(question, type);

            // then
            assertSoftly(softly -> {
                softly.assertThat(interview.getQuestion()).isEqualTo(question);
                softly.assertThat(interview.getInterviewType().getName()).isEqualTo(type);
            });
        }

        @Test
        void 없는_인터뷰_타입으로_수정_시_예외가_발생한다() {
            // given
            Interview interview = 인터뷰_나_일반_질문();
            String question = "변경_질문";
            String type = "abcd";

            // when & then
            assertThatThrownBy(() -> interview.updateInterview(question, type))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }
    }
}
