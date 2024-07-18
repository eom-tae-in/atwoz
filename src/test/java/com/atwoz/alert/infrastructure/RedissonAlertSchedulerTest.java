package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.helper.IntegrationHelper;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.AuditingHandler;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import static com.atwoz.alert.fixture.AlertFixture.알림_생성_id_없음;
import static com.atwoz.alert.fixture.AlertFixture.옛날_알림_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class RedissonAlertSchedulerTest extends IntegrationHelper {

    @Autowired
    private RedissonAlertScheduler redissonAlertScheduler;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private AlertRepository alertRepository;

    @Test
    void 생성된_지_60일을_초과한_알림은_삭제_상태로_된다() {
        // given
        Long memberId = 1L;

        LocalDateTime pastTime = LocalDateTime.now()
                .minusDays(61);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastTime));
        Alert savedOldAlert = alertRepository.save(옛날_알림_생성());

        auditingHandler.setDateTimeProvider(() -> Optional.of(LocalDateTime.now()));
        Alert savedAlert = alertRepository.save(알림_생성_id_없음());

        // when
        redissonAlertScheduler.deleteExpiredAlerts();

        // then
        Optional<Alert> foundSavedAlert = alertRepository.findByMemberIdAndId(memberId, savedAlert.getId());
        Optional<Alert> foundSavedOldAlert = alertRepository.findByMemberIdAndId(memberId, savedOldAlert.getId());

        assertSoftly(softly -> {
            softly.assertThat(foundSavedAlert).isPresent();
            softly.assertThat(foundSavedOldAlert).isPresent();
            Alert recentAlert = foundSavedAlert.get();
            Alert oldAlert = foundSavedOldAlert.get();
            softly.assertThat(recentAlert.getDeletedAt()).isNull();
            softly.assertThat(oldAlert.getDeletedAt()).isNotNull();
        });
    }

    @Test
    void 분산_락으로_중복호출을_막는다() throws InterruptedException {
        // given
        int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicLong atomicLong = new AtomicLong();

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    redissonAlertScheduler.deleteExpiredAlerts();
                    atomicLong.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(40, TimeUnit.SECONDS);
        executorService.shutdown();

        // then
        assertThat(atomicLong.get()).isEqualTo(1);
    }
}
