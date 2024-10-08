package com.atwoz.selfintro.application.dto;

import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record SelfIntroQueryResponses(
        List<SelfIntroQueryResponse> selfIntros,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    public static SelfIntroQueryResponses of(
            final Page<SelfIntroQueryResponse> selfIntros,
            final Pageable pageable,
            final int nextPage
    ) {
        return SelfIntroQueryResponses.builder()
                .selfIntros(selfIntros.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(selfIntros.getTotalPages())
                .totalElements(selfIntros.getTotalElements())
                .build();
    }
}
