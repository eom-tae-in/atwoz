package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
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
    public Optional<Member> findById(final Long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByPhoneNumber(final String phoneNumber) {
        return memberJpaRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public Optional<Member> findByNickname(final String nickname) {
        return memberJpaRepository.findByNickname(nickname);
    }

    @Override
    public MemberResponse findMemberWithProfile(final Long id) {
        return memberQueryRepository.findMemberWithProfile(id);
    }

    @Override
    public boolean existsById(final Long id) {
        return memberJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByPhoneNumber(final String phoneNumber) {
        return memberJpaRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public boolean existsByNickname(final String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public void deleteById(final Long id) {
        memberJpaRepository.deleteById(id);
    }
}
