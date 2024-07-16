package com.atwoz.member.domain.member.profile;

import com.atwoz.member.domain.member.profile.vo.Hobby;
import com.atwoz.member.exception.exceptions.member.profile.HobbyDuplicateException;
import com.atwoz.member.exception.exceptions.member.profile.HobbySizeException;
import com.atwoz.member.exception.exceptions.member.profile.InvalidHobbyException;
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
        void 유효하지_않은_취미_정보가_들어오면_예외가_발생한다(final String name,
                                          final List<String> invalidHobbyCodes,
                                          final Class<Exception> exception) {
            // when & then
            assertThatThrownBy(() -> memberHobbies.change(invalidHobbyCodes))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> generateInvalidCodesAndExceptions() {
            return Stream.of(
                    Arguments.of("취미의 수가 유효하지 않은 경우", List.of("B001", "B002", "B003", "B004"),
                            HobbySizeException.class),
                    Arguments.of("중복된 취미 코드가 존재하는 경우", List.of("B001", "B003", "B003"), HobbyDuplicateException.class),
                    Arguments.of("유효하지 않은 코드가 존재할 경우", List.of("B001", "B002", "ABCD"), InvalidHobbyException.class)
            );
        }

        @Test
        void 회원_취미_정보를_변경한다() {
            // given
            List<String> currenHobbyCodes = List.of("B001", "B002", "B003");
            memberHobbies.change(currenHobbyCodes);
            List<String> newHobbyCodes = List.of("B004", "B005", "B006");

            // when
            memberHobbies.change(newHobbyCodes);

            // then
            assertThat(memberHobbies.getHobbies()
                    .stream()
                    .map(MemberHobby::getHobby)
                    .map(Hobby::getCode))
                    .containsExactlyInAnyOrderElementsOf(newHobbyCodes);
        }
    }
}
