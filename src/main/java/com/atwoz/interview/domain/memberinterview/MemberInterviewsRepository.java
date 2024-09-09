package com.atwoz.interview.domain.memberinterview;

import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewResponse;

import java.util.List;
import java.util.Optional;

public interface MemberInterviewsRepository {

    MemberInterviews save(MemberInterviews memberInterviews);
    Optional<MemberInterviews> findByMemberId(Long memberId);
    List<MemberInterviewResponse> findMemberInterviewsByType(Long memberId, String type);
}
