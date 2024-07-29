package com.atwoz.alert.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlertJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void deleteExpiredAlerts() {
        String sql = "UPDATE alert"
                + " SET deleted_at = CURRENT_TIMESTAMP"
                + " WHERE created_at < TIMESTAMPADD(DAY, -61, CURRENT_TIMESTAMP)";
        jdbcTemplate.update(sql);
    }
}
