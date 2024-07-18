package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.profile.vo.Style;
import com.atwoz.member.exception.exceptions.member.profile.InvalidStyleException;
import com.atwoz.member.exception.exceptions.member.profile.StyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.StyleSizeException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberStylesTest {

    private MemberStyles memberStyles;

    @BeforeEach
    void setup() {
        memberStyles = new MemberStyles();
    }

    @Nested
    class 회원의_스타일_정보를_변경한다 {

        @ParameterizedTest(name = "{index}: {0}")
        @MethodSource("generateInvalidCodesAndExceptions")
        void 유효하지_않은_스타일_정보가_들어오면_예외가_발생한다(final String name,
                                           final List<String> invalidHobbyCodes,
                                           final Class<Exception> exception) {
            // when & then
            assertThatThrownBy(() -> memberStyles.change(invalidHobbyCodes))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> generateInvalidCodesAndExceptions() {
            return Stream.of(
                    Arguments.of("스타일의 수가 유효하지 않은 경우", List.of("C001", "C002", "C003", "C004"),
                            StyleSizeException.class),
                    Arguments.of("중복된 스타일 코드가 존재하는 경우", List.of("C001", "C003", "C003"), StyleDuplicateException.class),
                    Arguments.of("유효하지 않은 코드가 존재할 경우", List.of("C001", "C002", "ABCD"), InvalidStyleException.class)
            );
        }

        @Test
        void 회원_스타일_정보를_변경한다() {
            // given
            List<String> currentStyleCodes = List.of("C001", "C002", "C003");
            memberStyles.change(currentStyleCodes);
            List<String> newStyleCodes = List.of("C004", "C005", "C006");

            // when
            memberStyles.change(newStyleCodes);

            // then
            assertThat(memberStyles.getStyles()
                    .stream()
                    .map(MemberStyle::getStyle)
                    .map(Style::getCode))
                    .containsExactlyInAnyOrderElementsOf(newStyleCodes);
        }
    }
}
