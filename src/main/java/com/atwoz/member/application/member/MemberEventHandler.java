package com.atwoz.member.application.member;

import com.atwoz.member.application.member.event.ValidatedMemberExistenceEvent;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberEventHandler {

    private final MemberRepository memberRepository;

    @EventListener
    public void validateMemberExist(final ValidatedMemberExistenceEvent event) {
        if (memberRepository.existsById(event.memberId())) {
            throw new MemberNotFoundException();
        }
    }
}

