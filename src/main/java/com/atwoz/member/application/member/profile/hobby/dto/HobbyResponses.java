package com.atwoz.member.application.member.profile.hobby.dto;

import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyResponse;
import java.util.List;
import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Builder
public record HobbyResponses(
        List<HobbyResponse> hobbyResponses,
        int nowPage,
        int nextPage,
        int totalPages,
        long totalElements
) {

    private static final int NEXT_PAGE = 1;
    private static final int NO_MORE_PAGE = -1;

    public static HobbyResponses of(final Page<HobbyResponse> hobbyResponses,
                                    final Pageable pageable) {
        return HobbyResponses.builder()
                .hobbyResponses(hobbyResponses.getContent())
                .nowPage(pageable.getPageNumber())
                .nextPage(getNextPage(hobbyResponses.hasNext(), pageable.getPageNumber()))
                .totalPages(hobbyResponses.getTotalPages())
                .totalElements(hobbyResponses.getTotalElements())
                .build();
    }

    private static int getNextPage(final boolean hasNextPage,
                                   final int nowPage) {
        if (hasNextPage) {
            return nowPage + NEXT_PAGE;
        }

        return NO_MORE_PAGE;
    }
}
