package com.atwoz.interview.domain.memberinterview;

import java.util.Optional;

public interface MemberInterviewsRepository {

    MemberInterviews save(MemberInterviews memberInterviews);
    Optional<MemberInterviews> findByMemberId(Long memberId);
}
