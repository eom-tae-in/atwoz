package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Graduate;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGraduateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class GraduateTest {

    @DisplayName("학위_정보로_Graduate를_찾는다")
    @Nested
    class GraduateSearch {

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
