package com.atwoz.profile.domain.vo;

import com.atwoz.profile.exception.exceptions.InvalidMbtiException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MbtiTest {

    @Nested
    class Mbti_조회 {

        @Test
        void mbti_값이_유효하면_Mbti를_찾아_반환한다() {
            // given
            String validMbti = "INFP";

            // when
            Mbti mbti = Mbti.findByName(validMbti);

            // then
            assertThat(mbti.name()).isEqualTo(validMbti);
        }

        @Test
        void mbti_값이_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidMbti = "invalidMbti";

            // when & then
            assertThatThrownBy(() -> Mbti.findByName(invalidMbti))
                    .isInstanceOf(InvalidMbtiException.class);
        }
    }
}
