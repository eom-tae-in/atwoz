package com.atwoz.member.application.selfintro.dto;

import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record SelfIntroResponses(
        List<SelfIntroResponse> selfIntros,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    public static SelfIntroResponses of(
            final Page<SelfIntroResponse> selfIntros,
            final Pageable pageable,
            final int nextPage
    ) {
        return SelfIntroResponses.builder()
                .selfIntros(selfIntros.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(selfIntros.getTotalPages())
                .totalElements(selfIntros.getTotalElements())
                .build();
    }
}
