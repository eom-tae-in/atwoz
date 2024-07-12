package com.atwoz.alert.infrastructure.dto;

import java.time.LocalDateTime;

public record AlertSearchResponse(
        Long id,
        String group,
        AlertContentSearchResponse alert,
        Boolean isRead,
        LocalDateTime createdAt
) {
}
