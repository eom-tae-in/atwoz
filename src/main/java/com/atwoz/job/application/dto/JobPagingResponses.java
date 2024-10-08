package com.atwoz.job.application.dto;

import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record JobPagingResponses(
        List<JobPagingResponse> jobPagingResponses,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE = 1;
    private static final int NO_MORE_PAGE = -1;

    public static JobPagingResponses of(
            final Page<JobPagingResponse> jobPagingResponses,
            final Pageable pageable,
            final int nextPage
    ) {
        return JobPagingResponses.builder()
                .jobPagingResponses(jobPagingResponses.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(jobPagingResponses.getTotalPages())
                .totalElements(jobPagingResponses.getTotalElements())
                .build();
    }
}
