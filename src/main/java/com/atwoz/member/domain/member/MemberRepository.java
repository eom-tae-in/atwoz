package com.atwoz.member.domain.member;

import com.atwoz.member.infrastructure.member.dto.InternalProfileFilterRequest;
import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import com.atwoz.member.infrastructure.member.dto.ProfileResponse;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByNickname(String nickname);

    MemberResponse findMemberWithId(Long id);

    List<ProfileResponse> findTodayProfiles(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findProfileByPopularity(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findProfileByTodayVisit(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findNearbyProfile(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findRecentProfile(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findProfileByReligion(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    ProfileResponse findProfileByHobbies(InternalProfileFilterRequest internalProfileFilterRequest, Long memberId);

    Member save(Member member);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNickname(String nickname);

    boolean existsById(Long id);

    void deleteById(Long id);
}
