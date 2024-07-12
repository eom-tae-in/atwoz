package com.atwoz.alert.application;

import com.atwoz.alert.domain.AlertRepository;
import com.atwoz.alert.infrastructure.dto.AlertPagingResponse;
import com.atwoz.alert.infrastructure.dto.AlertSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlertQueryService {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    private final AlertRepository alertRepository;

    public AlertPagingResponse findMemberAlertsWithPaging(final Long memberId, final Pageable pageable) {
        Page<AlertSearchResponse> response = alertRepository.findMemberAlertsWithPaging(memberId, pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), response);
        return AlertPagingResponse.of(response, nextPage);
    }

    private int getNextPage(final int pageNumber, final Page<AlertSearchResponse> alerts) {
        if (alerts.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }
        return NO_MORE_PAGE;
    }
}
