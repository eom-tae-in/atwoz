package com.atwoz.member.domain.member.profile;

import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.hobby.MemberHobbySizeException;
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

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성_이름_코드;
import static com.atwoz.member.fixture.member.dto.request.InternalHobbiesInitializeRequestFixture.내부_취미_목록_초기화_요청_생성_취미목록;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberHobbiesTest {

    private MemberHobbies memberHobbies;

    @BeforeEach
    void setup() {
        memberHobbies = new MemberHobbies();
    }

    @Nested
    class 회원의_취미_정보를_변경한다 {

        @ParameterizedTest(name = "{index}: {0}")
        @MethodSource("generateInvalidCodesAndExceptions")
        void 유효하지_않은_취미_정보가_들어오면_예외가_발생한다(
                final String name,
                final List<Hobby> invalidHobbies,
                final Class<Exception> exception
        ) {
            // when & then
            assertThatThrownBy(() -> memberHobbies.initialize(내부_취미_목록_초기화_요청_생성_취미목록(invalidHobbies)))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> generateInvalidCodesAndExceptions() {
            return Stream.of(
                    Arguments.of(
                            "취미의 수가 유효하지 않은 경우",
                            List.of(
                                    취미_생성_이름_코드("hobby1", "code1"),
                                    취미_생성_이름_코드("hobby2", "code2"),
                                    취미_생성_이름_코드("hobby3", "code3"),
                                    취미_생성_이름_코드("hobby4", "code4")
                            ),
                            MemberHobbySizeException.class
                    ),
                    Arguments.of(
                            "중복된 취미 코드가 존재하는 경우",
                            List.of(
                                    취미_생성_이름_코드("hobby1", "code1"),
                                    취미_생성_이름_코드("hobby3", "code3"),
                                    취미_생성_이름_코드("hobby3", "code3")

                            ),
                            MemberHobbyDuplicateException.class
                    )
            );
        }

        @Test
        void 회원_취미_정보를_변경한다() {
            // given
            List<Hobby> newHobbies = List.of(
                    취미_생성_이름_코드("hobby4", "code4"),
                    취미_생성_이름_코드("hobby5", "code5"),
                    취미_생성_이름_코드("hobby6", "code6")
            );

            // when
            memberHobbies.initialize(내부_취미_목록_초기화_요청_생성_취미목록(newHobbies));

            // then
            assertThat(memberHobbies.getHobbies()
                    .stream()
                    .map(MemberHobby::getHobby))
                    .containsExactlyInAnyOrderElementsOf(newHobbies);
        }
    }
}
