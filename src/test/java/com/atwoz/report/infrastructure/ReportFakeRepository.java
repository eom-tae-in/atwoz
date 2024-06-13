package com.atwoz.report.infrastructure;

import com.atwoz.report.domain.Report;
import com.atwoz.report.domain.ReportRepository;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReportFakeRepository implements ReportRepository {

    private final Map<Long, Report> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Optional<Report> findById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public Report save(final Report report) {
        Report savedReport = Report.builder()
                .id(id)
                .reportedUserId(report.getReportedUserId())
                .reporterId(report.getReporterId())
                .reportType(report.getReportType())
                .content(report.getContent())
                .createdAt(report.getCreatedAt())
                .updatedAt(report.getUpdatedAt())
                .build();
        map.put(id, savedReport);

        return map.get(id++);
    }

    @Override
    public void deleteProcessedOldReports() {
        map.entrySet()
                .removeIf(entry -> isProcessedOldReport(entry.getValue()));
    }

    private boolean isProcessedOldReport(final Report report) {
        LocalDateTime thirtyDaysAgo = LocalDateTime.now()
                .minusDays(30);

        return report.getUpdatedAt()
                .isBefore(thirtyDaysAgo);
    }
}
