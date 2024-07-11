package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.AlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertJpaRepository alertJpaRepository;

    @Override
    public Alert save(final Alert alert) {
        return alertJpaRepository.save(alert);
    }
}
