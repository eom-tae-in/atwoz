package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByPhoneNumber(final String phoneNumber);

    boolean existsByPhoneNumber(final String email);

    boolean existsByNickname(String nickname);

}

