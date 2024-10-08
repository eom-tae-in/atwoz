package com.atwoz.selfintro.application;

import com.atwoz.selfintro.application.dto.SelfIntroCreateRequest;
import com.atwoz.selfintro.application.dto.SelfIntroUpdateRequest;
import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.domain.SelfIntroRepository;
import com.atwoz.selfintro.exception.exceptions.SelfIntroNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SelfIntroService {

    private final SelfIntroRepository selfIntroRepository;

    public void saveSelfIntro(final SelfIntroCreateRequest selfIntroCreateRequest,
                              final Long memberId) {
        SelfIntro selfIntro = SelfIntro.createWith(memberId, selfIntroCreateRequest.content());
        selfIntroRepository.save(selfIntro);
    }

    public void updateSelfIntro(final SelfIntroUpdateRequest selfIntroUpdateRequest,
                                final Long selfIntroId,
                                final Long memberId) {
        SelfIntro foundSelfIntro = findSelfIntroById(selfIntroId);
        foundSelfIntro.validateSameWriter(memberId);
        foundSelfIntro.update(selfIntroUpdateRequest.content());
    }

    public void deleteSelfIntro(final Long selfIntroId, final Long memberId) {
        SelfIntro foundSelfIntro = findSelfIntroById(selfIntroId);
        foundSelfIntro.validateSameWriter(memberId);
        selfIntroRepository.deleteById(foundSelfIntro.getId());
    }

    private SelfIntro findSelfIntroById(final Long selfIntroId) {
        return selfIntroRepository.findById(selfIntroId)
                .orElseThrow(SelfIntroNotFoundException::new);
    }
}
