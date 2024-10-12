package com.atwoz.profile.domain;

import com.atwoz.profile.exception.exceptions.DuplicatedUserHobbyException;
import com.atwoz.profile.exception.exceptions.UserHobbySizeException;
import java.util.List;
import java.util.stream.Stream;
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
class UserHobbiesTest {

    @Nested
    class 회원_취미_목록_생성 {

        @Test
        void 회원_취미_목록을_생성한다() {
            // given
            List<String> hobbyCodes = List.of("code1", "code2", "code3");

            // when
            UserHobbies userHobbies = UserHobbies.createWith(hobbyCodes);

            // then
            List<String> result = userHobbies.getHobbies()
                    .stream()
                    .map(UserHobby::getHobbyCode)
                    .toList();

            assertThat(result).containsExactlyElementsOf(hobbyCodes);
        }

        @ParameterizedTest(name = "{index}: {0}")
        @MethodSource("generateInvalidCodesAndExceptions")
        void 유효하지_않은_취미_정보가_들어오면_예외가_발생한다(
                final String name,
                final List<String> invalidHobbies,
                final Class<Exception> exception
        ) {
            // when & then
            assertThatThrownBy(() -> UserHobbies.createWith(invalidHobbies))
                    .isInstanceOf(exception);
        }

        private static Stream<Arguments> generateInvalidCodesAndExceptions() {
            return Stream.of(
                    Arguments.of(
                            "취미의 수가 유효하지 않은 경우",
                            List.of("code1", "code2", "code3", "code4"),
                            UserHobbySizeException.class
                    ),
                    Arguments.of(
                            "중복된 취미 코드가 존재하는 경우",
                            List.of("code1", "code1", "code3"),
                            DuplicatedUserHobbyException.class
                    )
            );
        }
    }
//
//    @Test
//    void 회원_취미_목록을_변경한다() {
//        // given
//        UserHobbies userHobbies = UserHobbies.createWith(List.of("code1", "code2", "code3"));
//        List<String> updateCodes = List.of("code4", "code5", "code6");
//
//        // when
//        userHobbies.update(updateCodes);
//
//        // then
//        List<String> result = userHobbies.getHobbies()
//                .stream()
//                .map(UserHobby::getHobbyCode)
//                .toList();
//        assertThat(result).containsExactlyElementsOf(updateCodes);
//    }
}
