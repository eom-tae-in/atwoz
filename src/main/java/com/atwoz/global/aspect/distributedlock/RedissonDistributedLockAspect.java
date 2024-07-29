package com.atwoz.global.aspect.distributedlock;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Aspect
public class RedissonDistributedLockAspect {

    private static final String AROUND_VALUE = "@annotation(redissonDistributedLock)";
    private final RedissonClient redissonClient;

    @Around(value = AROUND_VALUE)
    public Object handleRedissonDistributedLock(final ProceedingJoinPoint joinPoint,
                                                final RedissonDistributedLock redissonDistributedLock) throws Throwable {
        String lockName = redissonDistributedLock.lockName();
        long waitTime = redissonDistributedLock.waitTime();
        long holdTime = redissonDistributedLock.holdTime();
        TimeUnit timeUnit = redissonDistributedLock.timeUnit();
        Class<? extends RuntimeException> exceptionClass = redissonDistributedLock.exceptionClass();
        Constructor<? extends RuntimeException> declaredConstructor = exceptionClass.getDeclaredConstructor();

        RLock lock = redissonClient.getLock(lockName);
        boolean isLocked = false;
        try {
            isLocked = lock.tryLock(waitTime, holdTime, timeUnit);
            if (isLocked) {
                return joinPoint.proceed();
            }
            throw declaredConstructor.newInstance();
        } catch (InterruptedException e) {
            throw declaredConstructor.newInstance();
        } finally {
            if (isLocked) {
                lock.unlock();
            }
        }
    }
}
