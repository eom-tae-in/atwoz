package com.atwoz.member.application.member;

import com.atwoz.member.application.member.dto.MemberContactInfoDuplicationCheckResponse;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.exception.exceptions.member.MemberContactInfoAlreadyExistedException;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public MemberNotificationsResponse findMemberNotifications(final Long memberId) {
        return memberRepository.findMemberNotifications(memberId);
    }

    public MemberAccountStatusResponse findMemberAccountStatus(final Long memberId) {
        return memberRepository.findMemberAccountStatus(memberId);
    }

    public MemberContactInfoResponse findMemberContactInfo(final Long memberId) {
        return memberRepository.findMemberContactInfo(memberId);
    }

    public MemberContactInfoDuplicationCheckResponse checkContactInfoDuplicated(
            final String contactType,
            final String contactValue
    ) {
        if (memberRepository.existsByContact(Contact.createWith(contactType, contactValue))) {
            throw new MemberContactInfoAlreadyExistedException();
        }

        return MemberContactInfoDuplicationCheckResponse.from(false);
    }
}
