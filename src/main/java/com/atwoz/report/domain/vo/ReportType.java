package com.atwoz.report.domain.vo;

import com.atwoz.report.exception.exceptions.InvalidReportTypeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ReportType {

    FAKE_PROFILE("허위 프로필", "01"),
    EXCESSIVE_SEXUAL_EXPRESSION("과도한 성적 표현", "02"),
    OFFENSIVE_EXPRESSION("욕설 및 불쾌함을 주는 표현", "03"),
    INAPPROPRIATE_IMAGES("부적절한 사진", "04"),
    PHONE_NUMBER_ON_PROFILE("프로필 내 연락처 기재", "05");

    private final String reason;
    private final String code;

    ReportType(final String reason, final String code) {
        this.reason = reason;
        this.code = code;
    }

    public static ReportType findByCode(final String code) {
        return Arrays.stream(values())
                .filter(reportType -> code.equals(reportType.code))
                .findFirst()
                .orElseThrow(InvalidReportTypeException::new);
    }
}
