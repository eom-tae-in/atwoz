package com.atwoz.memberlike.application;

import com.atwoz.global.event.Events;
import com.atwoz.member.application.member.event.ValidatedMemberExistEvent;
import com.atwoz.memberlike.application.dto.MemberLikeCreateRequest;
import com.atwoz.memberlike.domain.MemberLike;
import com.atwoz.memberlike.domain.MemberLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberLikeService {

    private final MemberLikeRepository memberLikeRepository;

    public void sendLike(final Long memberId, final MemberLikeCreateRequest request) {
        Events.raise(new ValidatedMemberExistEvent(request.memberId()));

        memberLikeRepository.save(MemberLike.createWith(memberId, request.memberId(), request.likeLevel()));
    }
}
