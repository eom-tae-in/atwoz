package com.atwoz.alert.infrastructure;

import com.atwoz.alert.application.AlertScheduler;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.exception.exceptions.AlertLockException;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedissonAlertScheduler implements AlertScheduler {

    private static final String MIDNIGHT = "0 0 0 * * ?";
    private static final String DELETE_ALERT_LOCK = "delete_alert_lock";
    private static final long WAIT_TIME = 0L;
    private static final long HOLD_TIME = 40L;

    private final AlertRepository alertRepository;
    private final RedissonClient redissonClient;

    @Scheduled(cron = MIDNIGHT)
    @Override
    public void deleteExpiredAlerts() {
        RLock lock = redissonClient.getLock(DELETE_ALERT_LOCK);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(WAIT_TIME, HOLD_TIME, TimeUnit.SECONDS);
            if (isLocked) {
                alertRepository.deleteExpiredAlerts();
                return;
            }
            throw new AlertLockException();
        } catch (InterruptedException e) {
            throw new AlertLockException();
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }
}
