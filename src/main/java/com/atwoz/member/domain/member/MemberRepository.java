package com.atwoz.member.domain.member;

import java.util.Optional;

public interface MemberRepository {

    Optional<Member> findById(final Long id);

    Optional<Member> findByPhoneNumber(final String phoneNumber);

    Member save(final Member member);

    boolean existsByPhoneNumber(final String phoneNumber);

    boolean existsByNickname(final String nickname);

    boolean existsById(final Long id);

    void deleteById(final Long id);
}
