package com.atwoz.report.domain.vo;

import com.atwoz.report.exception.exceptions.InvalidReportResultException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ReportResult {

    WAITING("대기중"),
    DENIED("거부"),
    WARN("경고"),
    BAN("정지");

    private final String name;

    ReportResult(final String name) {
        this.name = name;
    }

    public static ReportResult findByName(final String name) {
        return Arrays.stream(values())
                .filter(reportResult -> name.equals(reportResult.name))
                .findFirst()
                .orElseThrow(InvalidReportResultException::new);
    }
}
