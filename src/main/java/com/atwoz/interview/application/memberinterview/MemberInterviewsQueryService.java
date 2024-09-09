package com.atwoz.interview.application.memberinterview;

import com.atwoz.interview.domain.memberinterview.MemberInterviewsRepository;
import com.atwoz.interview.ui.memberinterview.dto.MemberInterviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberInterviewsQueryService {

    private final MemberInterviewsRepository memberInterviewsRepository;

    public List<MemberInterviewResponse> findMemberInterviewsByType(final Long memberId, final String type) {
        return memberInterviewsRepository.findMemberInterviewsByType(memberId, type);
    }
}
