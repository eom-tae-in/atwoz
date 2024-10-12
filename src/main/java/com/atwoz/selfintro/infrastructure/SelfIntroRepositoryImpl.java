package com.atwoz.selfintro.infrastructure;

import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.domain.SelfIntroRepository;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SelfIntroRepositoryImpl implements SelfIntroRepository {

    private final SelfIntroJpaRepository selfIntroJpaRepository;
    private final SelfIntroQueryRepository selfIntroQueryRepository;

    @Override
    public SelfIntro save(final SelfIntro selfIntro) {
        return selfIntroJpaRepository.save(selfIntro);
    }

    @Override
    public Optional<SelfIntro> findById(final Long id) {
        return selfIntroJpaRepository.findById(id);
    }

    @Override
    public Page<SelfIntroQueryResponse> findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final SelfIntroFilterRequest request,
            final Long memberId
    ) {
        return selfIntroQueryRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                request,
                memberId
        );
    }

    @Override
    public void deleteById(final Long id) {
        selfIntroJpaRepository.deleteById(id);
    }
}
