package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Smoke;
import com.atwoz.member.exception.exceptions.member.profile.InvalidSmokeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SmokeTest {

    @DisplayName("흡연_정보로_Smoke를_찾는다")
    @Nested
    class SmokeSearch {

        @Test
        void 흡연_정보가_유효하면_Smoke를_찾아_반환한다() {
            // given
            String validSmoke = "비흡연";

            // when
            Smoke smoke = Smoke.findByName(validSmoke);

            // then
            assertThat(smoke.getName()).isEqualTo(validSmoke);
        }

        @Test
        void 흡연_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidSmoke = "invalidSmoke";

            // when & then
            assertThatThrownBy(() -> Smoke.findByName(invalidSmoke))
                    .isInstanceOf(InvalidSmokeException.class);
        }
    }
}
