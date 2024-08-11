package com.atwoz.member.domain.member.profile;

import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StyleRepository {

    Style save(Style style);

    Optional<Style> findStyleById(Long styleId);

    Optional<Style> findStyleByName(String styleName);

    Optional<Style> findStyleByCode(String styleCode);

    Page<StyleResponse> findStylesWithPaging(Pageable pageable);

    void deleteStyleById(Long styleId);
}
