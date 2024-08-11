package com.atwoz.member.domain.member.profile;

import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.style.MemberStyleSizeException;
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

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성_이름_코드;
import static com.atwoz.member.fixture.member.dto.request.InternalStylesInitializeRequestFixture.내부_스타일_목록_초기화_요청_생성;
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
        void 유효하지_않은_스타일_정보가_들어오면_예외가_발생한다(
                final String name,
                final List<Style> invalidStyleCodes,
                final Class<Exception> exception
        ) {
            // when & then
            assertThatThrownBy(() -> memberStyles.initialize(내부_스타일_목록_초기화_요청_생성(invalidStyleCodes)))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> generateInvalidCodesAndExceptions() {
            return Stream.of(
                    Arguments.of(
                            "스타일의 수가 유효하지 않은 경우",
                            List.of(
                                    스타일_생성_이름_코드("style1", "code1"),
                                    스타일_생성_이름_코드("style2", "code2"),
                                    스타일_생성_이름_코드("style3", "code3"),
                                    스타일_생성_이름_코드("style4", "code4")
                            ),
                            MemberStyleSizeException.class
                    ),
                    Arguments.of(
                            "중복된 스타일 코드가 존재하는 경우",
                            List.of(
                                    스타일_생성_이름_코드("style1", "code1"),
                                    스타일_생성_이름_코드("style3", "code3"),
                                    스타일_생성_이름_코드("style3", "code3")
                            ),
                            MemberStyleDuplicateException.class
                    )
            );
        }

        @Test
        void 회원_스타일_정보를_변경한다() {
            // given
            List<Style> newStyles = List.of(
                    스타일_생성_이름_코드("style4", "code4"),
                    스타일_생성_이름_코드("style5", "code5"),
                    스타일_생성_이름_코드("style6", "code6")
            );

            // when
            memberStyles.initialize(내부_스타일_목록_초기화_요청_생성(newStyles));

            // then
            assertThat(memberStyles.getStyles()
                    .stream()
                    .map(MemberStyle::getStyle))
                    .containsExactlyInAnyOrderElementsOf(newStyles);
        }
    }
}
