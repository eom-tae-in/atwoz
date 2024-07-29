package com.atwoz.alert.infrastructure;

import com.atwoz.alert.application.AlertScheduler;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.exception.exceptions.AlertLockException;
import com.atwoz.global.aspect.distributedlock.RedissonDistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedissonAlertScheduler implements AlertScheduler {

    private static final String MIDNIGHT = "0 0 0 * * ?";
    private static final String DELETE_ALERT_LOCK = "delete_alert_lock";
    private static final long WAIT_TIME = 0L;
    private static final long HOLD_TIME = 40L;

    private final AlertRepository alertRepository;

    @Scheduled(cron = MIDNIGHT)
    @RedissonDistributedLock(lockName = DELETE_ALERT_LOCK, waitTime = WAIT_TIME,
            holdTime = HOLD_TIME, exceptionClass = AlertLockException.class)
    @Override
    public void deleteExpiredAlerts() {
        alertRepository.deleteExpiredAlerts();
    }
}
