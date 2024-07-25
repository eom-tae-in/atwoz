package com.atwoz.global.config.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Component
public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

    private static final String ASYNC_MESSAGE_PREFIX = "Async Error Message = ";

    @Override
    public void handleUncaughtException(final Throwable throwable, final Method method, final Object... params) {
        log.warn(ASYNC_MESSAGE_PREFIX + "{}", throwable.getMessage());
    }
}
