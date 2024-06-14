package com.atwoz.report.domain.vo;

import com.atwoz.report.exception.exceptions.InvalidReportResultException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.report.domain.vo.ReportResult.BAN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReportResultTest {

    @Nested
    class ReportResult_조회 {

        @Test
        void 신고_결과_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidReportResult = "invalid report result";

            // when & then
            assertThatThrownBy(() -> ReportResult.findByName(invalidReportResult))
                    .isInstanceOf(InvalidReportResultException.class);
        }

        @Test
        void 신고_결과_정보가_유효하면_ReportResult을_찾아_반환한다() {
            // given
            String validReportResult = BAN.getName();

            // when
            ReportResult foundReportResult = ReportResult.findByName(validReportResult);

            // then
            assertThat(foundReportResult.getName()).isEqualTo(validReportResult);
        }
    }
}
