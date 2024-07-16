package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidSmokeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class SmokeTest {

    @Nested
    class Smoke_조회 {

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
