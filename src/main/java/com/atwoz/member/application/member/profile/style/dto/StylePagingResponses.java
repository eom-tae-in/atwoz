package com.atwoz.member.application.member.profile.style.dto;

import com.atwoz.member.infrastructure.member.profile.style.dto.StylePagingResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record StylePagingResponses(
        List<StylePagingResponse> stylePagingResponses,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE = 1;
    private static final int NO_MORE_PAGE = -1;

    public static StylePagingResponses of(
            final Page<StylePagingResponse> styleResponses,
            final Pageable pageable,
            final int nextPage
    ) {
        return StylePagingResponses.builder()
                .stylePagingResponses(styleResponses.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(styleResponses.getTotalPages())
                .totalElements(styleResponses.getTotalElements())
                .build();
    }
}
