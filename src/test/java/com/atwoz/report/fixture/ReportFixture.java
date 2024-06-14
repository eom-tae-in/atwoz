package com.atwoz.report.fixture;

import com.atwoz.report.domain.Report;
import java.time.LocalDateTime;

import static com.atwoz.report.domain.vo.ReportResult.BAN;
import static com.atwoz.report.domain.vo.ReportResult.WAITING;
import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;

@SuppressWarnings("NonAsciiCharacters")
public class ReportFixture {

    private static final int DELETION_THRESHOLD = 31;
    private static final Long REPORTED_USER_ID = 1L;
    private static final Long REPORTER_ID = 2L;
    private static final String REPORT_CONTENT = "사진을 도용했어요!";

    public static Report 신고_생성() {
        return Report.builder()
                .reportedUserId(REPORTED_USER_ID)
                .reporterId(REPORTER_ID)
                .reportType(FAKE_PROFILE)
                .reportResult(WAITING)
                .content(REPORT_CONTENT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Report 처리된_신고_생성() {
        return Report.builder()
                .reportedUserId(REPORTED_USER_ID)
                .reporterId(REPORTER_ID)
                .reportType(FAKE_PROFILE)
                .reportResult(BAN)
                .content(REPORT_CONTENT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Report 처리된지_31일된_신고_생성() {
        return Report.builder()
                .reportedUserId(REPORTED_USER_ID)
                .reporterId(REPORTER_ID)
                .reportType(FAKE_PROFILE)
                .reportResult(BAN)
                .content(REPORT_CONTENT)
                .createdAt(LocalDateTime.now()
                        .minusDays(DELETION_THRESHOLD))
                .updatedAt(LocalDateTime.now()
                        .minusDays(DELETION_THRESHOLD))
                .build();
    }
}
