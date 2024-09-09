package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberInterviewsRepositoryImpl implements MemberInterviewsRepository {

    private final MemberInterviewsJpaRepository memberInterviewsJpaRepository;
    private final MemberInterviewsQueryRepository memberInterviewsQueryRepository;

    @Override
    public MemberInterviews save(final MemberInterviews memberInterviews) {
        return memberInterviewsJpaRepository.save(memberInterviews);
    }

    @Override
    public Optional<MemberInterviews> findByMemberId(final Long memberId) {
        return memberInterviewsJpaRepository.findById(memberId);
    }

    @Override
    public List<MemberInterviewResponse> findMemberInterviewsByType(final Long memberId, final String type) {
        return memberInterviewsQueryRepository.findMemberInterviewsByType(memberId, type);
    }
}
