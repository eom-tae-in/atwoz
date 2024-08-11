package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.profile.QStyle.style;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class StyleQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<StyleResponse> findStylesWithPaging(final Pageable pageable) {
        List<StyleResponse> fetch = jpaQueryFactory.select(
                        constructor(
                                StyleResponse.class,
                                style.name,
                                style.code
                        )
                ).from(style)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(style.count())
                .from(style);

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }
}
