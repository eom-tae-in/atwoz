package com.atwoz.alert.application;

import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import com.atwoz.global.application.BaseQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlertQueryService extends BaseQueryService<AlertSearchResponse> {

    private final AlertRepository alertRepository;

    public AlertPagingResponse findMemberAlertsWithPaging(final Long memberId, final Pageable pageable) {
        Page<AlertSearchResponse> response = alertRepository.findMemberAlertsWithPaging(memberId, pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), response);
        return AlertPagingResponse.of(response, nextPage);
    }
}
