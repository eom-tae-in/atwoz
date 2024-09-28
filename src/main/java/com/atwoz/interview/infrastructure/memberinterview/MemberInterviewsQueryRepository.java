package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.interview.Interview;
import com.atwoz.interview.domain.interview.vo.InterviewType;
import com.atwoz.interview.domain.memberinterview.MemberInterview;
import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import static com.atwoz.interview.domain.interview.QInterview.interview;
import static com.atwoz.interview.domain.memberinterview.QMemberInterview.memberInterview;
import static com.atwoz.interview.domain.memberinterview.QMemberInterviews.memberInterviews1;

@RequiredArgsConstructor
@Component
public class MemberInterviewsQueryRepository {

    private static final String EMPTY_ANSWER = "";

    private final JPAQueryFactory jpaQueryFactory;

    public MemberInterviewDetailResponse findMemberInterviewAnswer(final Long interviewId, final Long memberId) {
        Interview targetInterview = jpaQueryFactory.select(interview)
                .from(interview)
                .where(interview.id.eq(interviewId))
                .fetchOne();

        MemberInterview answeredMemberInterview = jpaQueryFactory.selectFrom(memberInterview)
                .innerJoin(memberInterviews1)
                .on(memberInterviews1.memberInterviews.contains(memberInterview))
                .leftJoin(memberInterview.interview, interview)
                .fetchJoin()
                .where(memberInterviews1.memberId.eq(memberId), interview.id.eq(interviewId))
                .fetchOne();

        String answer = Optional.ofNullable(answeredMemberInterview)
                .map(MemberInterview::getAnswer)
                .orElse(EMPTY_ANSWER);
        return new MemberInterviewDetailResponse(interviewId, targetInterview.getQuestion(), answer);
    }

    public List<MemberInterviewSimpleResponse> findMemberInterviewsByType(final Long memberId, final String type) {
        InterviewType interviewType = InterviewType.findByName(type);

        List<Interview> interviews = jpaQueryFactory.select(interview)
                .from(interview)
                .where(interview.interviewType.eq(interviewType))
                .orderBy(interview.id.asc())
                .fetch();

        MemberInterviews memberInterviews = jpaQueryFactory.selectFrom(memberInterviews1)
                .leftJoin(memberInterviews1.memberInterviews)
                .fetchJoin()
                .where(memberInterviews1.memberId.eq(memberId))
                .fetchOne();

        Set<Long> memberSubmittedInterviewIds = memberInterviews.getMemberInterviews()
                .stream()
                .map(MemberInterview::getInterviewId)
                .collect(Collectors.toSet());

        return interviews.stream()
                .map(interview -> new MemberInterviewSimpleResponse(
                        interview.getId(),
                        interview.getQuestion(),
                        memberSubmittedInterviewIds.contains(interview.getId())
                        ))
                .toList();
    }
}
