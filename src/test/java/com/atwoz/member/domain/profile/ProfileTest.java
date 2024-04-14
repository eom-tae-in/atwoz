package com.atwoz.member.domain.profile;

import com.atwoz.member.application.member.dto.MemberUpdateRequest;
import com.atwoz.member.domain.member.dto.MemberProfileDto;
import com.atwoz.member.domain.member.profile.Profile;
import com.atwoz.member.domain.member.profile.physical.YearManager;
import com.atwoz.member.domain.member.profile.vo.Hobby;
import com.atwoz.member.domain.member.profile.vo.Style;
import com.atwoz.member.exception.exceptions.member.profile.HobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.HobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.StyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.StyleSizeException;
import com.atwoz.member.infrastructure.member.physical.FakeYearManager;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.atwoz.member.fixture.MemberRequestFixture.회원_정보_수정_요청서_요청;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ProfileTest {

    private Profile profile;
    private YearManager yearManager;

    @BeforeEach
    void setup() {
        profile = Profile.createWith("남성");
        yearManager = new FakeYearManager();
    }

    @Nested
    class Profile_변경 {

        @Test
        void 프로필_정보가_유효하면_값이_변경된다() {
            // given
            MemberUpdateRequest memberUpdateRequest = 회원_정보_수정_요청서_요청();
            MemberProfileDto memberProfileDto = MemberProfileDto.createWith(
                    memberUpdateRequest.profileUpdateRequest(), yearManager);

            // when
            profile.change(memberProfileDto);

            // then
            assertSoftly(softly -> {
                softly.assertThat(profile.getJob().getCode()).isEqualTo(memberProfileDto.job());
                softly.assertThat(profile.getGraduate().getName()).isEqualTo(memberProfileDto.graduate());
                softly.assertThat(profile.getSmoke().getName()).isEqualTo(memberProfileDto.smoke());
                softly.assertThat(profile.getDrink().getName()).isEqualTo(memberProfileDto.drink());
                softly.assertThat(profile.getReligion().getName()).isEqualTo(memberProfileDto.religion());
                softly.assertThat(profile.getMbti().name()).isEqualTo(memberProfileDto.mbti());
                softly.assertThat(profile.getPhysicalProfile()).isNotNull();
                softly.assertThat(profile.getLocation().getCity()).isEqualTo(memberProfileDto.city());
                softly.assertThat(profile.getLocation().getSector()).isEqualTo(memberProfileDto.sector());

                List<String> hobbyCodes = profile.getMemberHobbies().getHobbies().stream()
                        .map(Hobby::getCode)
                        .toList();
                List<String> styleCodes = profile.getMemberStyles().getStyles().stream()
                        .map(Style::getCode)
                        .toList();
                softly.assertThat(hobbyCodes).containsExactlyInAnyOrderElementsOf(memberProfileDto.getHobbies());
                softly.assertThat(styleCodes).containsExactlyInAnyOrderElementsOf(memberProfileDto.getStyles());
            });
        }

        @ParameterizedTest(name = "{index}: {0}")
        @MethodSource("wrongCodesProvider")
        void 취미_또는_스타일_코드_값이_중복되거나_유효하지_않거나_선택한_개수가_형식에_맞지_않으면_예외가_발생한다(final String name,
                                                                        final List<String> hobbyCodes,
                                                                        final List<String> styleCodes,
                                                                        final Class<RuntimeException> exceptionClass) {
            // given
            MemberUpdateRequest memberUpdateRequest = 회원_정보_수정_요청서_요청(hobbyCodes, styleCodes);
            MemberProfileDto memberProfileDto = MemberProfileDto.createWith(
                    memberUpdateRequest.profileUpdateRequest(), yearManager);

            // when & then
            assertThatThrownBy(() -> profile.change(memberProfileDto))
                    .isInstanceOf(exceptionClass);
        }

        private static Stream<Arguments> wrongCodesProvider() {
            return Stream.of(
                    Arguments.of("취미 코드값이 유하하지 않은 경우", List.of("invalidCode1", "invalidCode2", "invalidCode3"),
                            List.of("C001", "C002", "C003"), InvalidHobbyException.class),
                    Arguments.of("스타일 코드값이 유하하지 않은 경우", List.of("B001", "B002", "B003"),
                            List.of("invalidCode1", "invalidCode2", "invalidCode3"), InvalidStyleException.class),
                    Arguments.of("취미 코드가 중복된 경우", List.of("B001", "B001", "B003"),
                            List.of("C001", "C002", "C003"), HobbyDuplicateException.class),
                    Arguments.of("스타일 코드가 중복된 경우", List.of("B001", "B002", "B003"),
                            List.of("C001", "C001", "C003"), StyleDuplicateException.class),
                    Arguments.of("취미 코드가 중복된 경우", List.of("B001", "B001", "B003"),
                            List.of("C001", "C002", "C003"), HobbyDuplicateException.class),
                    Arguments.of("선택한 취미 개수가 적은 경우", List.of(),
                            List.of("C001", "C002", "C003"), HobbySizeException.class),
                    Arguments.of("선택한 스타일 개수가 적은 경우", List.of("B001", "B002", "B003"),
                            List.of(), StyleSizeException.class),
                    Arguments.of("선택한 취미 개수가 많은 경우", List.of("B001", "B002", "B003", "B004"),
                            List.of("C001", "C002", "C003"), HobbySizeException.class),
                    Arguments.of("선택한 취미 개수가 많은 경우", List.of("B001", "B002", "B003"),
                            List.of("C001", "C002", "C003", "C004"), StyleSizeException.class)
            );
        }
    }
}
