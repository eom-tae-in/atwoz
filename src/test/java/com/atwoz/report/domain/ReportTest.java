package com.atwoz.report.domain;

import com.atwoz.report.exception.exceptions.InvalidReporterException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.report.domain.vo.ReportResult.BAN;
import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;
import static com.atwoz.report.fixture.ReportFixture.신고_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReportTest {

    @Nested
    class 신고_생성 {

        @Test
        void 신고를_생성한다() {
            // given
            Long profileOwnerId = 1L;
            Long reporterId = 2L;
            String reportType = FAKE_PROFILE.getCode();
            String content = "사진을 도용했어요";

            // when
            Report report = Report.createWith(profileOwnerId, reporterId, reportType, content);

            // then
            assertSoftly(softly -> {
                softly.assertThat(report.getReportedUserId()).isEqualTo(profileOwnerId);
                softly.assertThat(report.getReporterId()).isEqualTo(reporterId);
                softly.assertThat(report.getReportType().getCode()).isEqualTo(reportType);
                softly.assertThat(report.getContent()).isEqualTo(content);
            });
        }

        @Test
        void 신고자와_신고당한자의_정보가_일치하면_예외가_발생한다() {
            // given
            Long profileOwnerId = 1L;
            Long reporterId = 1L;
            String reportType = FAKE_PROFILE.getCode();
            String content = "사진을 도용했어요";

            // when & then
            assertThatThrownBy(() -> Report.createWith(profileOwnerId, reporterId, reportType, content))
                    .isInstanceOf(InvalidReporterException.class);
        }
    }

    @Test
    void 신고_결과를_변경한다() {
        // given
        Report report = 신고_생성();
        String reportResult = BAN.getName();

        // when
        report.updateReportResult(reportResult);

        // then
        assertThat(report.getReportResult().getName()).isEqualTo(reportResult);
    }
}
