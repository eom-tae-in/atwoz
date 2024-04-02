package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Mbti;
import com.atwoz.member.exception.exceptions.member.profile.InvalidMbtiException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MbtiTest {

    @DisplayName("mbti_값으로_Mbti를_찾는다")
    @Nested
    class MbtiSearch {

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
