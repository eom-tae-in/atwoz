package com.atwoz.report.fixture;

import com.atwoz.report.application.dto.ReportCreateRequest;

import static com.atwoz.report.domain.vo.ReportType.FAKE_PROFILE;

@SuppressWarnings("NonAsciiCharacters")
public class ReportCreateRequestFixture {

    public static ReportCreateRequest 신고_요청_생성() {
        return new ReportCreateRequest(1L, FAKE_PROFILE.getCode(), "사진을 도용했어요!");
    }
}
