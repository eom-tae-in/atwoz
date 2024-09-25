package com.atwoz.member.application.member.profile.hobby.dto;

import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record HobbyPagingResponses(
        List<HobbyPagingResponse> hobbyPagingResponses,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE = 1;
    private static final int NO_MORE_PAGE = -1;

    public static HobbyPagingResponses of(
            final Page<HobbyPagingResponse> hobbyResponses,
            final Pageable pageable,
            final int nextPage
    ) {
        return HobbyPagingResponses.builder()
                .hobbyPagingResponses(hobbyResponses.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(nextPage)
                .totalPages(hobbyResponses.getTotalPages())
                .totalElements(hobbyResponses.getTotalElements())
                .build();
    }
}
