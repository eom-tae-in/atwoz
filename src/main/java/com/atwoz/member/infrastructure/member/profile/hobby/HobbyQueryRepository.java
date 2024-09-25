package com.atwoz.member.infrastructure.member.profile.hobby;

import com.atwoz.member.infrastructure.member.profile.hobby.dto.HobbyPagingResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.atwoz.member.domain.member.profile.QHobby.hobby;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class HobbyQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<HobbyPagingResponse> findHobbiesWithPaging(final Pageable pageable) {
        List<HobbyPagingResponse> fetch = jpaQueryFactory.select(
                        constructor(
                                HobbyPagingResponse.class,
                                hobby.id,
                                hobby.name,
                                hobby.code
                        )
                ).from(hobby)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(hobby.count())
                .from(hobby);

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }
}
