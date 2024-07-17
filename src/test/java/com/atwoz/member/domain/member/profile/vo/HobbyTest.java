package com.atwoz.member.domain.member.profile.vo;

import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyTest {

    @Nested
    class Hobby_조회 {

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

    @Nested
    class Hobby_코드_검증 {

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
