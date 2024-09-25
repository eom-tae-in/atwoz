package com.atwoz.member.application.member.profile.style;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.member.application.member.profile.style.dto.StylePagingResponses;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import com.atwoz.member.infrastructure.member.profile.style.dto.StylePagingResponse;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class StyleQueryService extends BaseQueryService<StylePagingResponse> {

    private final StyleRepository styleRepository;

    public StyleSingleResponse findStyle(final Long styleId) {
        Style foundStyle = findStyleById(styleId);
        return StyleSingleResponse.from(foundStyle);
    }

    private Style findStyleById(final Long styleId) {
        return styleRepository.findStyleById(styleId)
                .orElseThrow(StyleNotFoundException::new);
    }

    public StylePagingResponses findStylesWithPaging(final Pageable pageable) {
        Page<StylePagingResponse> styleResponses = styleRepository.findStylesWithPaging(pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), styleResponses);
        return StylePagingResponses.of(styleResponses, pageable, nextPage);
    }
}
