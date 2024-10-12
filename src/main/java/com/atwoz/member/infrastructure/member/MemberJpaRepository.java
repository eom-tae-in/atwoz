package com.atwoz.member.infrastructure.member;

import com.atwoz.member.domain.member.Member;
import com.atwoz.member.domain.member.vo.Contact;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Member save(Member member);

    Optional<Member> findById(Long memberId);

    boolean existsById(Long memberId);

    boolean existsByContact(Contact contact);

    void deleteById(Long memberId);
}

