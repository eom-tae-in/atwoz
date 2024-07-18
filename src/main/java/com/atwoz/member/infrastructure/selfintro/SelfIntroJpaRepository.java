package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.selfintro.SelfIntro;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelfIntroJpaRepository extends JpaRepository<SelfIntro, Long> {

    SelfIntro save(SelfIntro selfIntro);

    Optional<SelfIntro> findById(Long selfIntroId);

    void deleteById(Long selfIntroId);
}
