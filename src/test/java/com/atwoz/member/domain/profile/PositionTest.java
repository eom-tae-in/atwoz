package com.atwoz.member.domain.profile;

import com.atwoz.member.domain.member.profile.Position;
import com.atwoz.member.exception.exceptions.member.profile.LatitudeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.LongitudeRangeException;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PositionTest {

    @Nested
    class Position_생성 {

        @Test
        void 위도_경도_값이_유효하면_Position을_생성한다() {
            // given
            BigDecimal latitude = BigDecimal.valueOf(10);
            BigDecimal longitude = BigDecimal.valueOf(10);

            // when
            Position position = Position.createWith(latitude, longitude);

            // then
            assertSoftly(softly -> {
                softly.assertThat(position.getLatitude()).isEqualTo(latitude);
                softly.assertThat(position.getLongitude()).isEqualTo(longitude);
            });
        }

        @Test
        void 위도_값이_유효하지_않으면_예외가_발생한다() {
            // given
            BigDecimal invalidLatitude = BigDecimal.valueOf(-100);
            BigDecimal longitude = BigDecimal.valueOf(10);

            // when & then
            assertThatThrownBy(() -> Position.createWith(invalidLatitude, longitude))
                    .isInstanceOf(LatitudeRangeException.class);
        }

        @Test
        void 경도_값이_유효하지_않으면_예외가_발생한다() {
            // given
            BigDecimal latitude = BigDecimal.valueOf(10);
            BigDecimal invalidLongitude = BigDecimal.valueOf(-200);

            // when & then
            assertThatThrownBy(() -> Position.createWith(latitude, invalidLongitude))
                    .isInstanceOf(LongitudeRangeException.class);
        }
    }
}
