package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.member.domain.member.profile.Style;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleJpaRepository extends JpaRepository<Style, Long> {

    Style save(Style style);

    Optional<Style> findStyleById(Long styleId);

    Optional<Style> findStyleByName(String styleName);

    Optional<Style> findStyleByCode(String styleCode);

    void deleteStyleById(Long styleId);
}
