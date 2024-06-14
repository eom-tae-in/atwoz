package com.atwoz.report.ui;

import com.atwoz.member.ui.auth.support.auth.AuthMember;
import com.atwoz.report.application.ReportService;
import com.atwoz.report.application.dto.ReportCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reports")
@RestController
public class ReportController {

    private final ReportService reportService;

    @PostMapping
    public ResponseEntity<Void> createReport(@RequestBody @Valid final ReportCreateRequest reportCreateRequest,
                                             @AuthMember final Long reporterId) {
        reportService.createReport(reportCreateRequest, reporterId);

        return ResponseEntity.ok()
                .build();
    }
}
