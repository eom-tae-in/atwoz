package com.atwoz.interview.application.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewSimpleResponse;
import com.atwoz.interview.infrastructure.memberinterview.dto.MemberInterviewDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberInterviewsQueryService {

    private final MemberInterviewsRepository memberInterviewsRepository;

    public MemberInterviewDetailResponse findMemberInterviewAnswer(final Long interviewId, final Long memberId) {
        return memberInterviewsRepository.findMemberInterviewAnswer(interviewId, memberId);
    }

    public List<MemberInterviewSimpleResponse> findMemberInterviewsByType(final Long memberId, final String type) {
        return memberInterviewsRepository.findMemberInterviewsByType(memberId, type);
    }
}
