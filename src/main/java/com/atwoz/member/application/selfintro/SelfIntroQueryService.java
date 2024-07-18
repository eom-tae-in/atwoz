package com.atwoz.member.application.selfintro;

import com.atwoz.member.application.selfintro.dto.SelfIntroFilterRequest;
import com.atwoz.member.application.selfintro.dto.SelfIntrosResponse;
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
public class SelfIntroQueryService {

    private final SelfIntroRepository selfIntroRepository;

    public SelfIntrosResponse findAllSelfIntrosWithPaging(final Pageable pageable,
                                                          final Long memberId) {
        Page<SelfIntroResponse> selfIntroResponses = selfIntroRepository.findAllSelfIntrosWithPaging(pageable, memberId);

        return SelfIntrosResponse.of(selfIntroResponses, pageable);
    }

    public SelfIntrosResponse findAllSelfIntrosWithPagingAndFiltering(final Pageable pageable,
                                                                      final SelfIntroFilterRequest selfIntroFilterRequest,
                                                                      final Long memberId) {
        Page<SelfIntroResponse> selfIntroResponses = selfIntroRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                selfIntroFilterRequest.minAge(),
                selfIntroFilterRequest.maxAge(),
                selfIntroFilterRequest.isOnlyOppositeGender(),
                selfIntroFilterRequest.getCities(),
                memberId
        );

        return SelfIntrosResponse.of(selfIntroResponses, pageable);
    }
}
