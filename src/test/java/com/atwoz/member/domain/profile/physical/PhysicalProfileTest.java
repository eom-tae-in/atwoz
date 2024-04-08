package com.atwoz.member.domain.profile.physical;

import com.atwoz.member.domain.member.dto.PhysicalProfileInfo;
import com.atwoz.member.domain.member.profile.physical.PhysicalProfile;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.exception.exceptions.member.profile.physical.AgeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.HeightRangeException;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PhysicalProfileTest {

    private PhysicalProfile physicalProfile;
    private YearManager yearManager;

    @BeforeEach
    void setup() {
        physicalProfile = PhysicalProfile.createWith("남성");
        yearManager = new FakeYearManager();
    }

    @DisplayName("신체 프로필 정보를 변경한다")
    @Nested
    class PhysicalProfileModification {

        @Test
        void 신체_프로필_정보가_유효하면_값이_변경된다() {
            // given
            int birthYear = 2000;
            int height = 170;
            PhysicalProfileInfo physicalProfileInfo = new PhysicalProfileInfo(birthYear, height, yearManager);

            // when
            physicalProfile.change(physicalProfileInfo);

            // then
            assertSoftly(softly -> {
                softly.assertThat(physicalProfile.getAge()).isEqualTo(yearManager.getCurrentYear() - birthYear);
                softly.assertThat(physicalProfile.getHeight()).isEqualTo(height);
            });
        }

        @ParameterizedTest
        @CsvSource(value = {"1950", "2050"})
        void 생년월일_값이_유효하지_않으면_예외가_발생한다(final int invalidBirthYear) {
            // given
            int height = 170;
            PhysicalProfileInfo physicalProfileInfo = new PhysicalProfileInfo(invalidBirthYear, height, yearManager);

            // when & then
            assertThatThrownBy(() -> physicalProfile.change(physicalProfileInfo))
                    .isInstanceOf(AgeRangeException.class);
        }

        @ParameterizedTest
        @CsvSource(value = {"130", "210"})
        void 키_값이_유효하지_않으면_예외가_발생한다(final int invalidHeight) {
            // given
            int birthYear = 2000;
            PhysicalProfileInfo physicalProfileInfo = new PhysicalProfileInfo(birthYear, invalidHeight, yearManager);

            // when & then
            assertThatThrownBy(() -> physicalProfile.change(physicalProfileInfo))
                    .isInstanceOf(HeightRangeException.class);
        }
    }
}
