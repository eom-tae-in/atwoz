package com.atwoz.member.domain.profile.vo;

import com.atwoz.member.domain.member.profile.vo.Style;
import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleTest {

    @Nested
    class Style_조회 {

        @Test
        void 스타일_코드_값이_유효하면_Style을_찾아_반환한다() {
            // given
            String validStyleCode = "C001";

            // when
            Style style = Style.findByCode(validStyleCode);

            // then
            assertThat(style.getCode()).isEqualTo(validStyleCode);
        }

        @Test
        void 스타일_코드_값이_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidStyleCode = "invalidStyleCode";

            // when & then
            assertThatThrownBy(() -> Style.findByCode(invalidStyleCode))
                    .isInstanceOf(InvalidStyleException.class);
        }
    }

    @Nested
    class Style_코드_검증 {

        @Test
        void 코드가_유효한_값이면_true를_반환한다() {
            // given
            String validStyleCode = "C001";

            // when
            boolean result = Style.isValidCode(validStyleCode);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 코드가_유효한_값이_아니면_false를_반환한다() {
            // given
            String invalidStyleCode = "invalidStyleCode";

            // when
            boolean result = Style.isValidCode(invalidStyleCode);

            // then
            assertThat(result).isFalse();
        }
    }
}
