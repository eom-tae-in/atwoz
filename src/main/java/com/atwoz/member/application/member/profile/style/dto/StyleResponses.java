package com.atwoz.member.application.member.profile.style.dto;

import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record StyleResponses(
        List<StyleResponse> styleResponses,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE = 1;
    private static final int NO_MORE_PAGE = -1;

    public static StyleResponses of(
            final Page<StyleResponse> styleResponses,
            final Pageable pageable,
            final int nextPage
    ) {
        return StyleResponses.builder()
                .styleResponses(styleResponses.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(styleResponses.getTotalPages())
                .totalElements(styleResponses.getTotalElements())
                .build();
    }
}
