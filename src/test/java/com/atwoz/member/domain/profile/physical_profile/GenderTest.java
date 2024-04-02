package com.atwoz.member.domain.profile.physical_profile;

import com.atwoz.member.domain.member.profile.physical_profile.Gender;
import com.atwoz.member.exception.exceptions.member.profile.InvalidGenderException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class GenderTest {


    @DisplayName("성별_값으로_Gender를_찾는다")
    @Nested
    class GenderSearch {

        @Test
        void 성별_값이_유효하면_Gender를_찾아_반환한다() {
            // given
            String validGender = "남성";

            // when
            Gender gender = Gender.findByName(validGender);

            // then
            assertThat(gender.getName()).isEqualTo(validGender);
        }

        @Test
        void 성별_값이_유효하지_않으면_들어오면_예외가_발생한다() {
            // given
            String invalidGender = "invalidGender";

            // when & then
            assertThatThrownBy(() -> Gender.findByName(invalidGender))
                    .isInstanceOf(InvalidGenderException.class);
        }
    }
}
