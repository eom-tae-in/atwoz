package com.atwoz.report.application;

import com.atwoz.report.application.dto.ReportCreateRequest;
import com.atwoz.report.domain.Report;
import com.atwoz.report.domain.ReportRepository;
import com.atwoz.report.domain.vo.ReportResult;
import com.atwoz.report.exception.exceptions.ReportNotFoundException;
import com.atwoz.report.infrastructure.ReportFakeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.report.domain.vo.ReportResult.BAN;
import static com.atwoz.report.fixture.ReportCreateRequestFixture.신고_요청_생성;
import static com.atwoz.report.fixture.ReportFixture.신고_생성;
import static com.atwoz.report.fixture.ReportFixture.처리된지_31일된_신고_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReportServiceTest {

    private ReportService reportService;
    private ReportRepository reportRepository;

    @BeforeEach
    void setup() {
        reportRepository = new ReportFakeRepository();
        reportService = new ReportService(reportRepository);
    }

    @Test
    void 불건전한_유저를_신고한다() {
        // given
        ReportCreateRequest reportCreateRequest = 신고_요청_생성();
        Long reportId = 1L;
        Long reporterId = 2L;

        // when
        reportService.createReport(reportCreateRequest, reporterId);

        // then
        Optional<Report> foundReport = reportRepository.findById(reportId);
        assertSoftly(softly -> {
            softly.assertThat(foundReport).isPresent();
            Report report = foundReport.get();
            softly.assertThat(report.getId()).isEqualTo(reportId);
            softly.assertThat(report.getReporterId()).isEqualTo(reporterId);
        });
    }

    @Nested
    class 신고_결과_변경 {

        @Test
        void 존재하지_않는_신고일_경우_예외가_발생한다() {
            // given
            Long notExistReportId = 1L;
            String reportResult = BAN.getName();

            // when & then
            assertThatThrownBy(() -> reportService.updateReportResult(reportResult, notExistReportId))
                    .isInstanceOf(ReportNotFoundException.class);
        }

        @Test
        void 접수된_신고의_결과를_변경한다() {
            // given
            Report savedReport = reportRepository.save(신고_생성());
            String reportResult = BAN.getName();

            // when
            reportService.updateReportResult(reportResult, savedReport.getId());

            // then
            ReportResult savedReportResult = savedReport.getReportResult();
            assertThat(savedReportResult.getName()).isEqualTo(reportResult);
        }
    }

    @Test
    void 처리된지_30일_이상_지난_신고는_삭제된다() {
        // given
        reportRepository.save(신고_생성());
        reportRepository.save(처리된지_31일된_신고_생성());

        // when
        reportService.deleteReport();

        // then
        Optional<Report> foundReport = reportRepository.findById(1L);
        Optional<Report> processedOldReport = reportRepository.findById(2L);

        assertSoftly(softly -> {
            softly.assertThat(foundReport).isPresent();
            softly.assertThat(processedOldReport).isNotPresent();
        });
    }
}
