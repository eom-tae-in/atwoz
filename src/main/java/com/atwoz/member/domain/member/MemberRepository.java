package com.atwoz.member.domain.member;

import com.atwoz.member.infrastructure.member.dto.MemberResponse;
import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(Long id);

    Optional<Member> findByPhoneNumber(String phoneNumber);

    Optional<Member> findByNickname(String nickname);

    MemberResponse findMemberWithProfile(Long id);

    Member save(Member member);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByNickname(String nickname);

    boolean existsById(Long id);

    void deleteById(Long id);
}
