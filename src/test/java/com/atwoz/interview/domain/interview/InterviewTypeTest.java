package com.atwoz.interview.domain.interview;

import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.exception.exceptions.InterviewTypeNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class InterviewTypeTest {

    @Nested
    class 인터뷰_타입_조회 {

        @Test
        void 인터뷰_타입_조회_성공() {
            // given
            String name = "연인";

            // when
            InterviewType findInterviewType = InterviewType.findByName(name);

            // then
            assertThat(findInterviewType.getName()).isEqualTo(name);
        }

        @Test
        void 없는_인터뷰_타입으로_조회_시_예외가_발생한다() {
            // given
            String name = "abc";

            // when & then
            assertThatThrownBy(() -> InterviewType.findByName(name))
                    .isInstanceOf(InterviewTypeNotFoundException.class);
        }
    }
}
