package com.atwoz.report.infrastructure;

import com.atwoz.config.TestAuditingTestConfig;
import com.atwoz.helper.IntegrationHelper;
import com.atwoz.report.domain.Report;
import com.atwoz.report.domain.ReportRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import static com.atwoz.report.fixture.ReportFixture.처리된_신고_생성;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@Import(TestAuditingTestConfig.class)
class ReportJdbcRepositoryTest extends IntegrationHelper {

    @Autowired
    private ReportJdbcRepository reportJdbcRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Test
    void 처리된지_30일이_지난_신고는_삭제된다() {
        // given
        Report report = 처리된_신고_생성();
        reportRepository.save(report);

        // when
        reportJdbcRepository.deleteProcessedOldReports();

        // then
        Optional<Report> foundReport = reportRepository.findById(1L);
        assertThat(foundReport).isEmpty();
    }
}
