package com.atwoz.selfintro.application;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.application.dto.SelfIntroQueryResponses;
import com.atwoz.selfintro.domain.SelfIntroRepository;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SelfIntroQueryService extends BaseQueryService<SelfIntroQueryResponse> {

    private final SelfIntroRepository selfIntroRepository;

    public SelfIntroQueryResponses findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final SelfIntroFilterRequest request,
            final Long memberId
    ) {
        Page<SelfIntroQueryResponse> selfIntroResponses = selfIntroRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                request,
                memberId
        );
        int nextPage = getNextPage(pageable.getPageNumber(), selfIntroResponses);

        return SelfIntroQueryResponses.of(selfIntroResponses, pageable, nextPage);
    }
}
