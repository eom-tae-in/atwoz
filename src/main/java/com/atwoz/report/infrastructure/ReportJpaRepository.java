package com.atwoz.report.infrastructure;

import com.atwoz.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportJpaRepository extends JpaRepository<Report, Long> {
}
