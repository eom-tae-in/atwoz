package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.List;
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
    public void deleteById(final Long id) {
        selfIntroJpaRepository.deleteById(id);
    }

    @Override
    public Page<SelfIntroResponse> findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final Integer minAge,
            final Integer maxAge,
            final Boolean isOnlyOppositeGender,
            final List<String> cities,
            final Long memberId
    ) {
        return selfIntroQueryRepository.findAllSelfIntrosWithPagingAndFiltering(
                pageable,
                minAge,
                maxAge,
                isOnlyOppositeGender,
                cities,
                memberId
        );
    }
}
