package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.domain.member.vo.Contact;
import com.atwoz.member.infrastructure.member.dto.MemberAccountStatusResponse;
import com.atwoz.member.infrastructure.member.dto.MemberContactInfoResponse;
import com.atwoz.member.infrastructure.member.dto.MemberNotificationsResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;

    @Override
    public Member save(final Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public Optional<Member> findById(final Long memberId) {
        return memberJpaRepository.findById(memberId);
    }

    @Override
    public MemberNotificationsResponse findMemberNotifications(final Long memberId) {
        return memberQueryRepository.findMemberNotifications(memberId);
    }

    @Override
    public MemberAccountStatusResponse findMemberAccountStatus(final Long memberId) {
        return memberQueryRepository.findMemberAccountStatus(memberId);
    }

    @Override
    public MemberContactInfoResponse findMemberContactInfo(final Long memberId) {
        return memberQueryRepository.findMemberContactInfo(memberId);
    }

    @Override
    public boolean existsById(final Long memberId) {
        return memberJpaRepository.existsById(memberId);
    }

    @Override
    public boolean existsByContact(final Contact contact) {
        return memberJpaRepository.existsByContact(contact);
    }

    @Override
    public void deleteById(final Long memberId) {
        memberJpaRepository.deleteById(memberId);
    }
}
