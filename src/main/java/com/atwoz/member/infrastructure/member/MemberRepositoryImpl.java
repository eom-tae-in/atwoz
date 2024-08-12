package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.MemberRepository;
import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final MemberJdbcRepository memberJdbcRepository;

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
    public MemberResponse findMemberWithId(final Long id) {
        return memberQueryRepository.findMemberWithProfile(id);
    }

    @Override
    public List<ProfileResponse> findTodayProfiles(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        return memberJdbcRepository.findTodayProfiles(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findProfileByPopularity(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        return memberQueryRepository.findProfileByPopularity(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findProfileByTodayVisit(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                   final Long memberId) {
        return memberQueryRepository.findProfileByTodayVisit(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findNearbyProfile(final InternalProfileFilterRequest internalProfileFilterRequest,
                                             final Long memberId) {
        return memberQueryRepository.findNearbyProfile(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findRecentProfile(final InternalProfileFilterRequest internalProfileFilterRequest,
                                             final Long memberId) {
        return memberQueryRepository.findRecentProfile(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findProfileByReligion(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                 final Long memberId) {
        return memberQueryRepository.findProfileByReligion(internalProfileFilterRequest, memberId);
    }

    @Override
    public ProfileResponse findProfileByHobbies(final InternalProfileFilterRequest internalProfileFilterRequest,
                                                final Long memberId) {
        return memberQueryRepository.findProfileByHobbies(internalProfileFilterRequest, memberId);
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
