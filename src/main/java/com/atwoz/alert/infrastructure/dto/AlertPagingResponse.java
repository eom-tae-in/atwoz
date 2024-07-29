package com.atwoz.alert.infrastructure.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record AlertPagingResponse(
        List<AlertSearchResponse> alerts,
        int nextPage
) {
    public static AlertPagingResponse of(final Page<AlertSearchResponse> alerts, final int nextPage) {
        return new AlertPagingResponse(alerts.getContent(), nextPage);
    }
}
