package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.selfintro.SelfIntro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfIntroJpaRepository extends JpaRepository<SelfIntro, Long> {
}
