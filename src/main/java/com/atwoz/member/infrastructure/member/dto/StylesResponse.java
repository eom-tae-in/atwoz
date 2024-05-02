package com.atwoz.member.infrastructure.member.dto;

import com.atwoz.member.domain.member.profile.vo.Style;
import java.util.List;

public record StylesResponse(
        List<String> styles
) {

    public static StylesResponse createWith(final List<Style> styles) {
        return new StylesResponse(styles.stream()
                .map(Style::getName)
                .toList());
    }
}
