package com.atwoz.report.fixture;

import com.atwoz.report.domain.Report;
import java.time.LocalDateTime;

import static com.atwoz.report.domain.vo.ReportResult.BAN;
import static com.atwoz.report.domain.vo.ReportResult.WAITING;
import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;

@SuppressWarnings("NonAsciiCharacters")
public class ReportFixture {

    public static Report 신고_생성() {

        return Report.builder()
                .reportedUserId(1L)
                .reporterId(2L)
                .reportType(FAKE_PROFILE)
                .reportResult(WAITING)
                .content("사진을 도용했어요!")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Report 처리된_신고_생성() {

        return Report.builder()
                .reportedUserId(1L)
                .reporterId(2L)
                .reportType(FAKE_PROFILE)
                .reportResult(BAN)
                .content("사진을 도용했어요!")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Report 처리된지_31일된_신고_생성() {

        return Report.builder()
                .reportedUserId(1L)
                .reporterId(2L)
                .reportType(FAKE_PROFILE)
                .reportResult(BAN)
                .content("사진을 도용했어요!")
                .createdAt(LocalDateTime.now()
                        .minusDays(32))
                .updatedAt(LocalDateTime.now()
                        .minusDays(31))
                .build();
    }
}
