package com.atwoz.report.infrastructure;

import com.atwoz.config.TestAuditingTestConfig;
import com.atwoz.report.domain.Report;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.atwoz.report.fixture.ReportFixture.신고_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@DataJpaTest
@Import(TestAuditingTestConfig.class)
class ReportJpaRepositoryTest {

    @Autowired
    private ReportJpaRepository reportJpaRepository;

    @Test
    void 신고_저장() {
        // given
        Report createdReport = 신고_생성();

        // when
        Report savedReport = reportJpaRepository.save(createdReport);

        // then
        assertThat(savedReport).usingRecursiveComparison().isEqualTo(createdReport);
    }

    @Test
    void 신고_이이디_값으로_단건_조회() {
        // given
        Report createdReport = 신고_생성();
        reportJpaRepository.save(createdReport);

        // when
        Optional<Report> foundReport = reportJpaRepository.findById(createdReport.getId());

        // then
        assertSoftly(softly -> {
            softly.assertThat(foundReport).isPresent();
            Report report = foundReport.get();
            softly.assertThat(report).usingRecursiveComparison().isEqualTo(createdReport);
        });
    }
}
