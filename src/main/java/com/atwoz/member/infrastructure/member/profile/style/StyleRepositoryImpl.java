package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StyleRepositoryImpl implements StyleRepository {

    private final StyleJpaRepository styleJpaRepository;
    private final StyleQueryRepository styleQueryRepository;

    @Override
    public Style save(final Style style) {
        return styleJpaRepository.save(style);
    }

    @Override
    public Optional<Style> findStyleById(final Long styleId) {
        return styleJpaRepository.findStyleById(styleId);
    }

    @Override
    public Optional<Style> findStyleByName(final String styleName) {
        return styleJpaRepository.findStyleByName(styleName);
    }

    @Override
    public Optional<Style> findStyleByCode(final String styleCode) {
        return styleJpaRepository.findStyleByCode(styleCode);
    }

    @Override
    public Page<StyleResponse> findStylesWithPaging(final Pageable pageable) {
        return styleQueryRepository.findStylesWithPaging(pageable);
    }

    @Override
    public void deleteStyleById(final Long styleId) {
        styleJpaRepository.deleteStyleById(styleId);
    }
}
