package com.atwoz.profile.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class UserHobbyTest {

    @Test
    void 회원_취미를_생성한다() {
        // given
        String hobbyCode = "code";

        // when
        UserHobby userHobby = UserHobby.createWith(hobbyCode);

        // then
        assertThat(userHobby.getHobbyCode()).isEqualTo(hobbyCode);
    }

    @Nested
    class 취미_코드_비교 {

        @Test
        void 취미_코드를_비교하여_일치하면_true를_반환한다() {
            // given
            UserHobby primaryUserHobby = UserHobby.createWith("code1");
            UserHobby secondaryUserHobby = UserHobby.createWith("code1");

            // when
            boolean result = primaryUserHobby.isSameCode(secondaryUserHobby);

            // then
            assertThat(result).isTrue();
        }

        @Test
        void 취미_코드를_비교하여_일치하지_않으면_false를_반환한다() {
            // given
            UserHobby primaryUserHobby = UserHobby.createWith("code1");
            UserHobby secondaryUserHobby = UserHobby.createWith("code2");

            // when
            boolean result = primaryUserHobby.isSameCode(secondaryUserHobby);

            // then
            assertThat(result).isFalse();
        }
    }
}
