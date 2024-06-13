package com.atwoz.report.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReportJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void deleteProcessedOldReports() {
        String sql = "DELETE FROM Report"
                + " WHERE updated_at < TIMESTAMPADD(DAY, -30, CURRENT_DATE)" +
                " AND report_result <> 'WAITING'";

        jdbcTemplate.update(sql);
    }
}
