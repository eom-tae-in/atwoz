package com.atwoz.alert.ui;

import com.atwoz.alert.application.AlertQueryService;
import com.atwoz.alert.application.AlertService;
import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.atwoz.member.ui.auth.support.AuthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RequiredArgsConstructor
@RequestMapping("/api/members/me/alerts")
@RestController
public class AlertController {

    private final AlertQueryService alertQueryService;
    private final AlertService alertService;

    @GetMapping
    public ResponseEntity<AlertPagingResponse> findMemberAlertsWithPaging(
            @AuthMember final Long memberId,
            @PageableDefault(sort = "created_at", direction = DESC) final Pageable pageable
    ) {
        return ResponseEntity.ok(alertQueryService.findMemberAlertsWithPaging(memberId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertSearchResponse> readAlert(@AuthMember final Long memberId, @PathVariable final Long id) {
        Alert alert = alertService.readAlert(memberId, id);
        return ResponseEntity.ok(AlertSearchResponse.from(alert));
    }
}
