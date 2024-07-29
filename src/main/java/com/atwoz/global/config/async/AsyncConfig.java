package com.atwoz.global.config.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    private static final String ASYNC_EXECUTOR = "asyncExecutor";
    private static final String ALERT_CALLBACK_EXECUTOR = "alertCallbackExecutor";
    private static final String THREAD_PREFIX_NAME = "ATWOZ_ASYNC_THREAD: ";
    private static final String CALLBACK_THREAD_PREFIX_NAME = "ATWOZ_ALERT_THREAD: ";
    private static final int DEFAULT_THREAD_SIZE = 30;
    private static final int MAX_THREAD_SIZE = 40;
    private static final int QUEUE_SIZE = 100;
    private static final boolean WAIT_TASK_COMPLETE = true;
    private static final int DEFAULT_CALLBACK_THREAD_SIZE = 10;

    @Bean(name = ASYNC_EXECUTOR)
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(DEFAULT_THREAD_SIZE);
        executor.setMaxPoolSize(MAX_THREAD_SIZE);
        executor.setQueueCapacity(QUEUE_SIZE);
        executor.setThreadNamePrefix(THREAD_PREFIX_NAME);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_TASK_COMPLETE);
        executor.initialize();
        return executor;
    }

    @Bean(name = ALERT_CALLBACK_EXECUTOR)
    public Executor getAsyncCallbackExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(DEFAULT_CALLBACK_THREAD_SIZE);
        executor.setThreadNamePrefix(CALLBACK_THREAD_PREFIX_NAME);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_TASK_COMPLETE);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncExceptionHandler();
    }
}
