package com.atwoz.alert.domain;

import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AlertRepository {

    Alert save(Alert alert);
    Page<AlertSearchResponse> findMemberAlertsWithPaging(Long memberId, Pageable pageable);
    Optional<Alert> findByMemberIdAndId(Long memberId, Long id);
    void deleteExpiredAlerts();
}
