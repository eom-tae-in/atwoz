package com.atwoz.alert.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.ThreadManager;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Component
public class FirebaseThreadManager extends ThreadManager {

    private static final int FIREBASE_THREADS_SIZE = 40;

    @Override
    protected ExecutorService getExecutor(final FirebaseApp firebaseApp) {
        return Executors.newFixedThreadPool(FIREBASE_THREADS_SIZE);
    }

    @Override
    protected void releaseExecutor(final FirebaseApp firebaseApp, final ExecutorService executorService) {
        executorService.shutdownNow();
    }

    @Override
    protected ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }
}
