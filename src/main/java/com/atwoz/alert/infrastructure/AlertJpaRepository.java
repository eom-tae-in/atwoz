package com.atwoz.alert.infrastructure;

import com.atwoz.alert.domain.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertJpaRepository extends JpaRepository<Alert, Long> {

    Alert save(Alert alert);
}
