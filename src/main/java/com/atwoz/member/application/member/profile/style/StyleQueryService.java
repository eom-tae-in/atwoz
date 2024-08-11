package com.atwoz.member.application.member.profile.style;

import com.atwoz.member.application.member.profile.style.dto.StyleResponses;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StyleQueryService {

    private final StyleRepository styleRepository;

    public StyleResponse findStyle(final Long styleId) {
        Style foundStyle = findStyleById(styleId);
        return StyleResponse.from(foundStyle);
    }

    private Style findStyleById(final Long styleId) {
        return styleRepository.findStyleById(styleId)
                .orElseThrow(StyleNotFoundException::new);
    }

    public StyleResponses findStylesWithPaging(final Pageable pageable) {
        Page<StyleResponse> styleResponses = styleRepository.findStylesWithPaging(pageable);
        return StyleResponses.of(styleResponses, pageable);
    }
}
