package com.atwoz.member.domain.member.profile.physical;

import com.atwoz.member.domain.member.dto.initial.InternalPhysicalProfileInitializeRequest;
import com.atwoz.member.domain.member.dto.update.InternalPhysicalProfileUpdateRequest;
import com.atwoz.member.exception.exceptions.member.profile.physical.AgeRangeException;
import com.atwoz.member.exception.exceptions.member.profile.physical.HeightRangeException;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.atwoz.member.fixture.member.dto.request.InternalPhysicalProfileInitializeRequestFixture.내부_신체_프로필_초기화_요청_생성_생년월일_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalPhysicalProfileInitializeRequestFixture.내부_신체_프로필_초기화_요청_생성_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalPhysicalProfileInitializeRequestFixture.내부_신체_프로필_초기화_요청_생성_키_연도관리자;
import static com.atwoz.member.fixture.member.dto.request.InternalPhysicalProfileUpdateRequestFixture.내부_신체_프로필_업데이트_요청_생성_연도관리자;
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

    @Nested
    class 신체_프로필_정보_초기화 {

        @Test
        void 신체_프로필_정보가_유효하면_값이_초기화된다() {
            // given
            InternalPhysicalProfileInitializeRequest initializeRequest = 내부_신체_프로필_초기화_요청_생성_연도관리자(yearManager);

            // when
            physicalProfile.initialize(initializeRequest);

            // then
            assertSoftly(softly -> {
                softly.assertThat(physicalProfile.getAge())
                        .isEqualTo(yearManager.getCurrentYear() - initializeRequest.birthYear());
                softly.assertThat(physicalProfile.getHeight())
                        .isEqualTo(initializeRequest.height());
            });
        }

        @ParameterizedTest
        @CsvSource(value = {"1950", "2050"})
        void 생년월일_값이_유효하지_않으면_예외가_발생한다(final int invalidBirthYear) {
            // given
            InternalPhysicalProfileInitializeRequest initializeRequest = 내부_신체_프로필_초기화_요청_생성_생년월일_연도관리자(
                    invalidBirthYear, yearManager);

            // when & then
            assertThatThrownBy(() -> physicalProfile.initialize(initializeRequest))
                    .isInstanceOf(AgeRangeException.class);
        }

        @ParameterizedTest
        @CsvSource(value = {"130", "210"})
        void 키_값이_유효하지_않으면_예외가_발생한다(final int invalidHeight) {
            // given
            InternalPhysicalProfileInitializeRequest initializeRequest = 내부_신체_프로필_초기화_요청_생성_키_연도관리자(
                    invalidHeight, yearManager);

            // when & then
            assertThatThrownBy(() -> physicalProfile.initialize(initializeRequest))
                    .isInstanceOf(HeightRangeException.class);
        }
    }

    @Test
    void 신체_프로필_정보가_유효하면_값이_변경된다() {
        // given
        InternalPhysicalProfileUpdateRequest updateRequest = 내부_신체_프로필_업데이트_요청_생성_연도관리자(yearManager);

        // when
        physicalProfile.update(updateRequest);

        // then
        assertSoftly(softly -> {
            softly.assertThat(physicalProfile.getAge())
                    .isEqualTo(yearManager.getCurrentYear() - updateRequest.birthYear());
            softly.assertThat(physicalProfile.getHeight())
                    .isEqualTo(updateRequest.height());
        });
    }
}
