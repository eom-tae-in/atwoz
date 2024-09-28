package com.atwoz.interview.infrastructure.interview;

import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.infrastructure.interview.dto.InterviewResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import static com.atwoz.interview.domain.interview.QInterview.interview;
import static com.querydsl.core.types.Projections.constructor;

@RequiredArgsConstructor
@Component
public class InterviewQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<InterviewResponse> findByInterviewType(final InterviewType interviewType) {
        return jpaQueryFactory.select(constructor(InterviewResponse.class,
                interview.id,
                interview.question,
                interview.interviewType))
                .from(interview)
                .where(interview.interviewType.eq(interviewType))
                .orderBy(interview.id.asc())
                .fetch();
    }
}
