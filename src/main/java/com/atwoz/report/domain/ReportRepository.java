package com.atwoz.report.domain;

import java.util.Optional;

public interface ReportRepository {

    Optional<Report> findById(Long id);

    Report save(Report report);

    void deleteProcessedOldReports();
}
