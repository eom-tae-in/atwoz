package com.atwoz.config;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.AuditingHandler;

@TestConfiguration
public class TestAuditingTestConfig {

    private static final int DELETION_THRESHOLD = 31;

    @Autowired
    private AuditingHandler auditingHandler;

    @Bean
    public AuditingHandler customAuditingHandler() {
        LocalDateTime pastDate = LocalDateTime.now()
                .minusDays(DELETION_THRESHOLD);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastDate));

        return auditingHandler;
    }
}
