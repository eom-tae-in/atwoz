package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class MemberInterviewsRepositoryImpl implements MemberInterviewsRepository {

    private final MemberInterviewsJpaRepository memberInterviewsJpaRepository;

    @Override
    public MemberInterviews save(final MemberInterviews memberInterviews) {
        return memberInterviewsJpaRepository.save(memberInterviews);
    }

    @Override
    public Optional<MemberInterviews> findByMemberId(final Long memberId) {
        return memberInterviewsJpaRepository.findById(memberId);
    }
}
