package com.atwoz.member.infrastructure.member.profile.hobby;

import com.atwoz.member.domain.member.profile.Hobby;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.member.fixture.member.domain.HobbyFixture.취미_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class HobbyJpaRepositoryTest {

    @Autowired
    private HobbyJpaRepository hobbyJpaRepository;

    private Hobby hobby;

    @BeforeEach
    void setup() {
        hobby = 취미_생성();
    }

    @Test
    void 취미를_저장한다() {
        // when
        Hobby savedHobby = hobbyJpaRepository.save(hobby);

        // then
        assertThat(savedHobby).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(hobby);
    }

    @Nested
    class 취미_조회 {
        @Test
        void 취미를_id로_조회한다() {
            // given
            Hobby savedHobby = hobbyJpaRepository.save(hobby);
            Long hobbyId = savedHobby.getId();

            // when
            Optional<Hobby> foundHobby = hobbyJpaRepository.findHobbyById(hobbyId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundHobby).isPresent();
                softly.assertThat(savedHobby)
                        .usingRecursiveComparison()
                        .isEqualTo(foundHobby.get());
            });
        }

        @Test
        void 취미를_이름으로_조회한다() {
            // given
            Hobby savedHobby = hobbyJpaRepository.save(hobby);
            String hobbyName = savedHobby.getName();

            // when
            Optional<Hobby> foundHobby = hobbyJpaRepository.findHobbyByName(hobbyName);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundHobby).isPresent();
                softly.assertThat(savedHobby)
                        .usingRecursiveComparison()
                        .isEqualTo(foundHobby.get());
            });
        }

        @Test
        void 취미를_코드로_조회한다() {
            // given
            Hobby savedHobby = hobbyJpaRepository.save(hobby);
            String hobbyCode = savedHobby.getCode();

            // when
            Optional<Hobby> foundHobby = hobbyJpaRepository.findHobbyByCode(hobbyCode);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundHobby).isPresent();
                softly.assertThat(savedHobby)
                        .usingRecursiveComparison()
                        .isEqualTo(foundHobby.get());
            });
        }
    }

    @Test
    void 취미를_삭제한다() {
        // given
        Hobby savedHobby = hobbyJpaRepository.save(hobby);
        Long hobbyId = savedHobby.getId();

        // when
        hobbyJpaRepository.deleteHobbyById(hobbyId);

        // then
        Optional<Hobby> foundHobby = hobbyJpaRepository.findHobbyById(hobbyId);
        assertThat(foundHobby).isEmpty();
    }
}
