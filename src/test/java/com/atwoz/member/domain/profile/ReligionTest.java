package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Religion;
import com.atwoz.member.exception.exceptions.member.profile.InvalidReligionException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReligionTest {

    @Nested
    class Religion_조회 {

        @Test
        void 종교_정보가_유효하면_Religion을_찾아_반환한다() {
            // given
            String validReligion = "기독교";

            // when
            Religion religion = Religion.findByName(validReligion);

            // then
            assertThat(religion.getName()).isEqualTo(validReligion);
        }

        @Test
        void 종교_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidReligion = "invalidReligion";

            // when & then
            assertThatThrownBy(() -> Religion.findByName(invalidReligion))
                    .isInstanceOf(InvalidReligionException.class);
        }
    }
}
