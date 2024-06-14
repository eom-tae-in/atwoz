package com.atwoz.report.infrastructure;

import com.atwoz.report.domain.Report;
import com.atwoz.report.domain.ReportRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReportRepositoryImpl implements ReportRepository {

    private final ReportJdbcRepository reportJdbcRepository;
    private final ReportJpaRepository reportJpaRepository;

    @Override
    public Optional<Report> findById(final Long id) {
        return reportJpaRepository.findById(id);
    }

    @Override
    public Report save(final Report report) {
        return reportJpaRepository.save(report);
    }

    @Override
    public void deleteProcessedOldReports() {
        reportJdbcRepository.deleteProcessedOldReports();
    }
}
