package com.atwoz.global.aspect.distributedlock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedissonDistributedLock {
    String lockName();
    long waitTime();
    long holdTime();
    TimeUnit timeUnit() default TimeUnit.SECONDS;
    Class<? extends RuntimeException> exceptionClass();
}
