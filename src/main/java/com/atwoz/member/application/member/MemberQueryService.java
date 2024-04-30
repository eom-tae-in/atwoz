package com.atwoz.member.application.member;

import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberResponse findMember(final Long memberId) {
        return memberRepository.findMemberWithProfile(memberId);
    }
}
