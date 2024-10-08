package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.MemberAccountStatusPatchRequest;
import com.atwoz.member.application.member.dto.MemberContactInfoPatchRequest;
import com.atwoz.member.application.member.dto.MemberNotificationsPatchRequest;
import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.exception.exceptions.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public void create(final String phoneNumber) {
        //TODO: 입력이 유효한지 검증하는 로직 추가되어야함

        memberRepository.save(Member.createWithOAuth(phoneNumber));
    }

    public void patchMemberNotifications(final Long memberId, final MemberNotificationsPatchRequest request) {
        Member foundMember = findMember(memberId);
        foundMember.updatePushNotifications(
                request.isLikeReceivedNotificationOn(),
                request.isNewMessageNotificationOn(),
                request.isProfileExchangeNotificationOn(),
                request.isProfileImageChangeNotificationOn(),
                request.isLongTimeLoLoginNotificationOn(),
                request.isInterviewWritingRequestNotificationOn()
        );
    }

    public void patchMemberAccountStatus(final Long memberId, final MemberAccountStatusPatchRequest request) {
        Member foundMember = findMember(memberId);
        foundMember.updateAccountStatus(request.status());
    }

    public void patchMemberContact(final Long memberId, final MemberContactInfoPatchRequest request) {
        Member foundMember = findMember(memberId);
        foundMember.updateContact(request.contactType(), request.contactValue());
    }


    private Member findMember(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
    }


    public void deleteMember(final Long memberId) {
        checkMemberExists(memberId);
        memberRepository.deleteById(memberId);
    }

    private void checkMemberExists(final Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new MemberNotFoundException();
        }
    }
}
