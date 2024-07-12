package com.atwoz.alert.infrastructure.dto;

import com.atwoz.alert.domain.Alert;
import com.atwoz.alert.domain.vo.AlertGroup;

import java.time.LocalDateTime;

public record AlertSearchResponse(
        Long id,
        AlertGroup group,
        AlertContentSearchResponse alert,
        Boolean isRead,
        LocalDateTime createdAt
) {

    public static AlertSearchResponse from(final Alert alert) {
        return new AlertSearchResponse(alert.getId(),
                alert.getAlertGroup(),
                new AlertContentSearchResponse(alert.getTitle(), alert.getBody()),
                alert.getIsRead(),
                alert.getCreatedAt()
        );
    }
}
