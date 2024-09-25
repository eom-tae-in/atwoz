package com.atwoz.member.application.auth;

import com.atwoz.alert.application.event.AlertTokenCreatedEvent;
import com.atwoz.global.event.Events;
import com.atwoz.member.application.auth.dto.LoginRequest;
import com.atwoz.member.application.auth.dto.TestLoginRequest;
import com.atwoz.member.domain.auth.MemberTokenProvider;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.auth.dto.MemberInfoResponse;
import com.atwoz.member.infrastructure.auth.dto.OAuthProviderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class MemberAuthService {

    private static final String DEFAULT_PHONE_NUMBER = "01011111111";

    private final MemberTokenProvider memberTokenProvider;
    private final OAuthRequester oAuthRequester;
    private final MemberRepository memberRepository;

    /**
     * OAuth 인증방식과 PASS 인증 방식에 차이가 존재해서 회의 후 메서드 변경을 진행할 예정
     */
    public String login(final LoginRequest request, final OAuthProviderRequest provider) {
        String accessToken = oAuthRequester.getAccessToken(request.code(), provider);
        MemberInfoResponse memberInfoResponse = oAuthRequester.getMemberInfo(accessToken, provider);
        Member createdMember = Member.createWithOAuth(DEFAULT_PHONE_NUMBER);
        memberRepository.save(createdMember);
        createdMember.updateVisitStatus();
        Events.raise(new AlertTokenCreatedEvent(createdMember.getId(), request.token()));

        return memberTokenProvider.createAccessToken(createdMember.getId());
    }

    //TODO: test를 위해 임시로 구현한 메서드입니다. 나중에 삭제해야합니다.
    public String testLogin(final TestLoginRequest request) {
        Member member = Member.createWithOAuth(request.phoneNumber());
        memberRepository.save(member);

        return memberTokenProvider.createAccessToken(member.getId());
    }
}
