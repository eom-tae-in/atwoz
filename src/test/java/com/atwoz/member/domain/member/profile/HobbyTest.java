package com.atwoz.member.domain.member.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class HobbyTest {

    private Hobby hobby;

    @BeforeEach
    void setup() {
        hobby = 취미_생성();
    }

    @Test
    void 이름과_코드_정보로_취미를_생성한다() {
        // given
        String hobbyName = "hobby1";
        String hobbyCode = "code1";

        // when
        Hobby createdHobby = Hobby.createWith(hobbyName, hobbyCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(createdHobby.getName())
                    .isEqualTo(hobbyName);
            softly.assertThat(createdHobby.getCode())
                    .isEqualTo(hobbyCode);
        });
    }

    @Test
    void 취미의_이름과_코드를_업데이트한다() {
        // given
        String updateHobbyName = "hobby2";
        String updateHobbyCode = "code2";

        // when
        hobby.update(updateHobbyName, updateHobbyCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(hobby.getName())
                    .isEqualTo(updateHobbyName);
            softly.assertThat(hobby.getCode())
                    .isEqualTo(updateHobbyCode);
        });
    }

    @Test
    void 취미_정보가_일치하면_true를_반환한다() {
        // given
        Hobby newHobby = 취미_생성();

        // when
        boolean result = hobby.isSame(newHobby);

        // then
        assertThat(result).isTrue();
    }
}
