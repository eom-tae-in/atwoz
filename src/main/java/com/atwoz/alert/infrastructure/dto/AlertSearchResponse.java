package com.atwoz.alert.infrastructure.dto;

import com.atwoz.alert.domain.vo.AlertGroup;

import java.time.LocalDateTime;

public record AlertSearchResponse(
        Long id,
        AlertGroup group,
        AlertContentSearchResponse alert,
        Boolean isRead,
        LocalDateTime createdAt
) {
}
