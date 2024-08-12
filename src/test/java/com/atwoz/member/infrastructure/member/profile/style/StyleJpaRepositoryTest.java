package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.member.domain.member.profile.Style;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class StyleJpaRepositoryTest {

    @Autowired
    private StyleJpaRepository styleJpaRepository;

    private Style style;

    @BeforeEach
    void setup() {
        style = 스타일_생성();
    }

    @Test
    void 스타일을_저장한다() {
        // when
        Style savedStyle = styleJpaRepository.save(style);

        // then
        assertThat(savedStyle).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(style);
    }

    @Nested
    class 스타일_조회 {

        @Test
        void 스타일을_id로_조회한다() {
            // given
            Style savedStyle = styleJpaRepository.save(style);
            Long styleId = savedStyle.getId();

            // when
            Optional<Style> foundStyle = styleJpaRepository.findStyleById(styleId);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundStyle).isPresent();
                softly.assertThat(savedStyle)
                        .usingRecursiveComparison()
                        .isEqualTo(foundStyle.get());
            });
        }

        @Test
        void 스타일을_이름으로_조회한다() {
            // given
            Style savedStyle = styleJpaRepository.save(style);
            String styleName = savedStyle.getName();

            // when
            Optional<Style> foundStyle = styleJpaRepository.findStyleByName(styleName);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundStyle).isPresent();
                softly.assertThat(savedStyle)
                        .usingRecursiveComparison()
                        .isEqualTo(foundStyle.get());
            });
        }

        @Test
        void 스타일을_코드로_조회한다() {
            // given
            Style savedStyle = styleJpaRepository.save(style);
            String styleCode = savedStyle.getCode();

            // when
            Optional<Style> foundStyle = styleJpaRepository.findStyleByCode(styleCode);

            // then
            assertSoftly(softly -> {
                softly.assertThat(foundStyle).isPresent();
                softly.assertThat(savedStyle)
                        .usingRecursiveComparison()
                        .isEqualTo(foundStyle.get());
            });
        }
    }

    @Test
    void 스타일을_삭제한다() {
        // given
        Style savedStyle = styleJpaRepository.save(style);
        Long styleId = savedStyle.getId();

        // when
        styleJpaRepository.deleteStyleById(styleId);

        // then
        Optional<Style> foundStyle = styleJpaRepository.findStyleById(styleId);
        assertThat(foundStyle).isEmpty();
    }
}
