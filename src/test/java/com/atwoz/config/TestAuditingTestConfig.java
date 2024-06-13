package com.atwoz.config;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.AuditingHandler;

@TestConfiguration
public class TestAuditingTestConfig {

    private static final int THIRTY_ONE = 31;

    @Autowired
    private AuditingHandler auditingHandler;

    @Bean
    public AuditingHandler customAuditingHandler() {
        LocalDateTime thirtyOneDaysAgo = LocalDateTime.now()
                .minusDays(THIRTY_ONE);
        auditingHandler.setDateTimeProvider(() -> Optional.of(thirtyOneDaysAgo));

        return auditingHandler;
    }
}
