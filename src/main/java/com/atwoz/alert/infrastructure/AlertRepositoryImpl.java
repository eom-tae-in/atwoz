package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertJpaRepository alertJpaRepository;
    private final AlertJdbcRepository alertJdbcRepository;
    private final AlertQueryRepository alertQueryRepository;

    @Override
    public Alert save(final Alert alert) {
        return alertJpaRepository.save(alert);
    }

    @Override
    public Page<AlertSearchResponse> findMemberAlertsWithPaging(final Long memberId, final Pageable pageable) {
        return alertQueryRepository.findMemberAlertsWithPaging(memberId, pageable);
    }

    @Override
    public Optional<Alert> findByMemberIdAndId(final Long memberId, final Long id) {
        return alertQueryRepository.findByMemberIdAndId(memberId, id);
    }

    @Override
    public void deleteExpiredAlerts() {
        alertJdbcRepository.deleteExpiredAlerts();
    }
}
