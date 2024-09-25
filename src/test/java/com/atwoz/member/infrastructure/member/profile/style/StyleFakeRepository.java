package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.style.dto.StylePagingResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class StyleFakeRepository implements StyleRepository {

    private final Map<Long, Style> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public Style save(final Style style) {
        Style createdStyle = Style.builder()
                .id(id)
                .name(style.getName())
                .code(style.getCode())
                .build();
        map.put(id++, createdStyle);

        return createdStyle;
    }

    @Override
    public Optional<Style> findStyleById(final Long styleId) {
        return Optional.ofNullable(map.get(styleId));
    }

    @Override
    public Optional<Style> findStyleByName(final String styleName) {
        return map.values()
                .stream()
                .filter(style -> styleName.equals(style.getName()))
                .findAny();
    }

    @Override
    public Optional<Style> findStyleByCode(final String styleCode) {
        return map.values()
                .stream()
                .filter(style -> styleCode.equals(style.getCode()))
                .findAny();
    }

    @Override
    public Page<StylePagingResponse> findStylesWithPaging(final Pageable pageable) {
        List<StylePagingResponse> stylePagingResponses = map.values()
                .stream()
                .sorted(Comparator.comparing(Style::getId))
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(StylePagingResponse::from)
                .toList();

        return new PageImpl<>(stylePagingResponses);
    }

    @Override
    public void deleteStyleById(final Long styleId) {
        map.remove(styleId);
    }
}
