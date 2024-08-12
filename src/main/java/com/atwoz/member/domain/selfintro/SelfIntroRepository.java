package com.atwoz.member.domain.selfintro;

import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SelfIntroRepository {

    SelfIntro save(SelfIntro selfIntro);

    Optional<SelfIntro> findById(Long id);

    void deleteById(Long id);

    Page<SelfIntroResponse> findAllSelfIntrosWithPaging(Pageable pageable);

    Page<SelfIntroResponse> findAllSelfIntrosWithPagingAndFiltering(
            Pageable pageable,
            int minAge,
            int maxAge,
            boolean isOnlyOppositeGender,
            List<String> cities,
            Long memberId
    );
}
