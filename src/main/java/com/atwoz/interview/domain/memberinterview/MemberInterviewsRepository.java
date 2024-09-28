package com.atwoz.interview.domain.memberinterview;

import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;

import java.util.List;
import java.util.Optional;

public interface MemberInterviewsRepository {

    MemberInterviews save(MemberInterviews memberInterviews);
    Optional<MemberInterviews> findByMemberId(Long memberId);
    List<MemberInterviewSimpleResponse> findMemberInterviewsByType(Long memberId, String type);
}
