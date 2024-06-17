package com.atwoz.member.application.member;

import com.atwoz.member.application.auth.event.ValidatedLoginEvent;
import com.atwoz.member.application.member.event.ValidatedMemberExistEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MemberEventHandler {

    private final MemberService memberService;

    @EventListener
    public void registerIfNotMemberExist(final ValidatedLoginEvent event) {
        memberService.create(event.getPhoneNumber());
    }

    @EventListener
    public void validateMemberExist(final ValidatedMemberExistEvent event) {
        memberService.validateMemberExist(event.memberId());
    }
}

