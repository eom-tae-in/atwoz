package com.atwoz.global.application;

import org.springframework.data.domain.Page;

public abstract class BaseQueryService<T> {

    private static final int NEXT_PAGE_INDEX = 1;
    private static final int NO_MORE_PAGE = -1;

    protected int getNextPage(final int pageNumber, final Page<T> page) {
        if (page.hasNext()) {
            return pageNumber + NEXT_PAGE_INDEX;
        }
        return NO_MORE_PAGE;
    }
}
