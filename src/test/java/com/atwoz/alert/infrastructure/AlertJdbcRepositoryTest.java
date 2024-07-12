package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.helper.IntegrationHelper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import static com.atwoz.alert.fixture.AlertFixture.옛날_알림_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class AlertJdbcRepositoryTest extends IntegrationHelper {

    private static final int MINUS_DAY_FOR_DELETE_ALERT = 61;

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void 생성된_지_60일이_초과된_알림은_삭제_상태로_변경된다() {
        // given
        LocalDateTime pastTime = LocalDateTime.now()
                .minusDays(MINUS_DAY_FOR_DELETE_ALERT);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastTime));

        Alert alert = 옛날_알림_생성();
        entityManager.persist(alert);
        entityManager.flush();
        entityManager.clear();

        // when
        alertRepository.deleteExpiredAlerts();

        // then
        Alert findAlert = entityManager.find(Alert.class, alert.getId());
        assertThat(findAlert.getDeletedAt()).isNotNull();
    }
}
