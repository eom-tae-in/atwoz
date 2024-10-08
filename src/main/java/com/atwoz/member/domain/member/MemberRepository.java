package com.atwoz.member.domain.member;

import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long memberId);

    MemberNotificationsResponse findMemberNotifications(Long memberId);

    MemberAccountStatusResponse findMemberAccountStatus(Long memberId);

    MemberContactInfoResponse findMemberContactInfo(Long memberId);

    boolean existsById(Long memberId);

    boolean existsByContact(Contact contact);

    void deleteById(Long memberId);
}
