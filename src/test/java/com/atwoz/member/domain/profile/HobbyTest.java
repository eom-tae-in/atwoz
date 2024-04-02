package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Hobby;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyTest {


    @DisplayName("취미_코드_값으로_Hobby를_찾는다")
    @Nested
    class HobbySearch {

        @Test
        void 취미_코드_값이_유효하면_코드_값으로_Hobby를_찾는다() {
            // given
            String validHobbyCode = "B001";

            // when
            Hobby hobby = Hobby.findByCode(validHobbyCode);

            // then
            assertThat(hobby.getCode()).isEqualTo(validHobbyCode);
        }

        @Test
        void 취미_코드_값이_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidHobbyCode = "invalidHobbyCode";

            // when & then
            assertThatThrownBy(() -> Hobby.findByCode(invalidHobbyCode))
                    .isInstanceOf(InvalidHobbyException.class);
        }
    }

    @DisplayName("취미_코드가_유효하면_true_유효하지_않으면_false를_반환한다")
    @Nested
    class HobbyCodeValidation {

        @Test
        void 코드가_유효한_값이면_true를_반환한다() {
            // given
            String validHobbyCode = "B001";

            // when
            boolean result = Hobby.isValidCode(validHobbyCode);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 코드가_유효한_값이_아니면_false를_반환한다() {
            // given
            String invalidHobbyCode = "invalidHobbyCode";

            // when
            boolean result = Hobby.isValidCode(invalidHobbyCode);

            // then
            assertThat(result).isFalse();
        }
    }
}
