package com.atwoz.job.infrasturcture;

import com.atwoz.job.infrasturcture.dto.JobPagingResponse;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import static com.atwoz.job.domain.QJob.job;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Repository
public class JobQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public Page<JobPagingResponse> findJobWithPaging(final Pageable pageable) {
        List<JobPagingResponse> fetch = jpaQueryFactory.select(
                        constructor(
                                JobPagingResponse.class,
                                job.id,
                                job.name,
                                job.code
                        )
                ).from(job)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory.select(job.count())
                .from(job);

        return PageableExecutionUtils.getPage(fetch, pageable, count::fetchOne);
    }
}
