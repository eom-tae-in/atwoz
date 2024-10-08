package com.atwoz.profile.domain.vo;

import com.atwoz.profile.domain.YearManager;
import com.atwoz.profile.infrastructure.FakeYearManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PhysicalProfileTest {

    private YearManager yearManager;

    @BeforeEach
    void setup() {
        yearManager = new FakeYearManager();
    }

    @Nested
    class 신체_프로필_생성 {

        @Test
        void 신체_프로필을_생성한다() {
            // given
            int birthYear = 2000;
            int height = 170;
            String gender = "남성";

            // when
            PhysicalProfile physicalProfile = PhysicalProfile.createWith(birthYear, height, gender, yearManager);

            // then
            assertSoftly(softly -> {
                softly.assertThat(physicalProfile.getAge()).isEqualTo(yearManager.getCurrentYear() - birthYear);
                softly.assertThat(physicalProfile.getHeight()).isEqualTo(height);
                softly.assertThat(physicalProfile.getGender()).isEqualTo(Gender.findByName(gender));
            });
        }

//        @ParameterizedTest
//        @CsvSource(value = {"1950", "2050"})
//        void 생년월일_값이_유효하지_않으면_예외가_발생한다(final int invalidBirthYear) {
//            // when & then
//            assertThatThrownBy(() -> PhysicalProfile.createWith(invalidBirthYear, 170, "남성", yearManager))
//                    .isInstanceOf(AgeRangeException.class);
//        }
//
//        @ParameterizedTest
//        @CsvSource(value = {"130", "210"})
//        void 키_값이_유효하지_않으면_예외가_발생한다(final int invalidHeight) {
//            // when & then
//            assertThatThrownBy(() -> PhysicalProfile.createWith(2000, invalidHeight, "남성", yearManager))
//                    .isInstanceOf(AgeRangeException.class);
//        }
    }
}
