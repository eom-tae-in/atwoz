package com.atwoz.member.application.selfintro;

import com.atwoz.global.application.BaseQueryService;
import com.atwoz.member.application.selfintro.dto.SelfIntroFilterRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntroResponses;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SelfIntroQueryService extends BaseQueryService<SelfIntroResponse> {

    private final SelfIntroRepository selfIntroRepository;

    public SelfIntroResponses findAllSelfIntrosWithPaging(final Pageable pageable) {
        Page<SelfIntroResponse> selfIntroResponses = selfIntroRepository.findAllSelfIntrosWithPaging(pageable);
        int nextPage = getNextPage(pageable.getPageNumber(), selfIntroResponses);
        return SelfIntroResponses.of(selfIntroResponses, pageable, nextPage);
    }

    public SelfIntroResponses findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final SelfIntroFilterRequest selfIntroFilterRequest,
            final Long memberId
    ) {
        Page<SelfIntroResponse> selfIntroResponses = selfIntroRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                selfIntroFilterRequest.minAge(),
                selfIntroFilterRequest.maxAge(),
                selfIntroFilterRequest.isOnlyOppositeGender(),
                selfIntroFilterRequest.getCities(),
                memberId
        );
        int nextPage = getNextPage(pageable.getPageNumber(), selfIntroResponses);

        return SelfIntroResponses.of(selfIntroResponses, pageable, nextPage);
    }
}
