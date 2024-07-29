package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.atwoz.alert.fixture.AlertFixture.알림_생성_id_없음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
class AlertJpaRepositoryTest {

    @Autowired
    private AlertJpaRepository alertJpaRepository;

    @Test
    void 알림_생성() {
        // given
        Alert alert = 알림_생성_id_없음();

        // when
        Alert savedAlert = alertJpaRepository.save(alert);

        // then
        assertThat(alert).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(savedAlert);
    }
}
