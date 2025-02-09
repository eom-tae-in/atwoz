package com.atwoz.report.domain.vo;

import com.atwoz.report.exception.exceptions.InvalidReportTypeException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class ReportTypeTest {

    @Nested
    class ReportType_조회{

        @Test
        void 신고_유형_정보가_유효하지_않으면_예외가_발생한다() {
            // given
            String invalidReportType = "invalid report type";

            // when & then
            assertThatThrownBy(() -> ReportType.findByCode(invalidReportType))
                    .isInstanceOf(InvalidReportTypeException.class);
        }

        @Test
        void 신고_유형_정보가_유효하면_ReportType을_찾아_반환한다() {
            // given
            String validReportType = FAKE_PROFILE.getCode();

            // when
            ReportType foundReportType = ReportType.findByCode(validReportType);

            // then
            assertThat(foundReportType.getCode()).isEqualTo(validReportType);
        }
    }
}
