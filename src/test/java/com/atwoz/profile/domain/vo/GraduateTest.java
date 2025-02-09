package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidGraduateException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class GraduateTest {

    @Nested
    class Graduate_조회 {

        @Test
        void 학위_정보가_유효하면_정보_값으로_Graduate를_찾는다() {
            // given
            String validGraduate = "석사";

            // when
            Graduate graduate = Graduate.findByName(validGraduate);

            // then
            assertThat(graduate.getName()).isEqualTo(validGraduate);
        }

        @Test
        void 학위_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidGraduate = "invalidGraduate";

            // when & then
            assertThatThrownBy(() -> Graduate.findByName(invalidGraduate))
                    .isInstanceOf(InvalidGraduateException.class);
        }
    }
}
