package com.atwoz.member.application.selfintro.dto;

import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public record SelfIntrosResponse(
        List<SelfIntroResponse> selfIntros,
        int nowPage,
        int nextPage,
        int totalPages
) {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    public static SelfIntrosResponse of(final Page<SelfIntroResponse> selfIntros,
                                        final Pageable pageable) {
        return new SelfIntrosResponse(
                selfIntros.getContent(),
                pageable.getPageNumber(),
                getNextPage(pageable.getPageNumber(), selfIntros),
                selfIntros.getTotalPages()
        );
    }

    private static int getNextPage(final int pageNumber, final Page<SelfIntroResponse> selfIntros) {
        if (selfIntros.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }

        return NO_MORE_PAGE;
    }
}
