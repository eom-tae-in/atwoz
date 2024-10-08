package com.atwoz.selfintro.domain;

import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SelfIntroRepository {

    SelfIntro save(SelfIntro selfIntro);

    Optional<SelfIntro> findById(Long id);

    void deleteById(Long id);

    Page<SelfIntroQueryResponse> findAllSelfIntrosWithPagingAndFiltering(
            Pageable pageable,
            SelfIntroFilterRequest request,
            Long memberId
    );
}
