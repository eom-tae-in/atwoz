package com.atwoz.interview.infrastructure.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInterviewsJpaRepository extends JpaRepository<MemberInterviews, Long> {

    MemberInterviews save(MemberInterviews memberInterviews);
    Optional<MemberInterviews> findById(Long memberId);
}
