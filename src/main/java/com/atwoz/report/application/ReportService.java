package com.atwoz.report.application;

import com.atwoz.report.application.dto.ReportCreateRequest;
import com.atwoz.report.domain.Report;
import com.atwoz.report.domain.ReportRepository;
import com.atwoz.report.exception.exceptions.ReportNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ReportService {

    private final ReportRepository reportRepository;

    public void createReport(final ReportCreateRequest reportCreateRequest, final Long reporterId) {
        Report report = Report.createWith(reportCreateRequest.reportedUserId(), reporterId,
                reportCreateRequest.reportTypeCode(), reportCreateRequest.content());

        reportRepository.save(report);
    }

    public void updateReportResult(final String reportResult, final Long reportId) {
        Report foundReport = findReportById(reportId);
        foundReport.updateReportResult(reportResult);
    }

    private Report findReportById(final Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(ReportNotFoundException::new);
    }

    @Scheduled(cron = "0 31 0 * * ?")
    public void deleteReport() {
        reportRepository.deleteProcessedOldReports();
    }
}
