package com.atwoz.selfintro.infrastructure;

import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.fixture.셀프소개글_픽스처;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class SelfIntroJpaRepositoryTest {

    @Autowired
    private SelfIntroJpaRepository selfIntroJpaRepository;

    private SelfIntro selfIntro;

    @BeforeEach
    void setup() {
        selfIntro = 셀프소개글_픽스처.셀프소개글_생성();
    }

    @Test
    void 셀프_소개글을_저장한다() {
        // when
        SelfIntro savedSelfIntro = selfIntroJpaRepository.save(selfIntro);

        // then
        assertThat(savedSelfIntro).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(selfIntro);
    }

    @Test
    void 셀프_소개글을_id로_조회한다() {
        // given
        SelfIntro savedSelfIntro = selfIntroJpaRepository.save(selfIntro);

        // when
        Optional<SelfIntro> foundSelfIntro = selfIntroJpaRepository.findById(savedSelfIntro.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundSelfIntro).isPresent();
            SelfIntro result = foundSelfIntro.get();
            softly.assertThat(result).usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(selfIntro);
        });
    }

    @Test
    void 셀프_소개글을_id로_삭제한다() {
        // given
        SelfIntro savedSelfIntro = selfIntroJpaRepository.save(selfIntro);

        // when
        selfIntroJpaRepository.deleteById(savedSelfIntro.getId());

        // then
        assertThat(selfIntroJpaRepository.findById(1L)).isEmpty();
    }
}
