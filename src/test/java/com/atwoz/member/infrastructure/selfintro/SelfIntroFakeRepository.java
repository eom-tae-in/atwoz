package com.atwoz.member.infrastructure.selfintro;

import com.atwoz.member.domain.selfintro.SelfIntro;
import com.atwoz.member.domain.selfintro.SelfIntroRepository;
import com.atwoz.member.infrastructure.selfintro.dto.SelfIntroResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.atwoz.member.fixture.selfintro.SelfIntroResponseFixture.셀프_소개글_응답;

@SuppressWarnings("NonAsciiCharacters")
public class SelfIntroFakeRepository implements SelfIntroRepository {

    private static final int AGE = 30;
    private static final String CITY = "서울시";
    private static final int SIZE_PER_PAGE = 10;

    private final Map<Long, SelfIntro> map = new HashMap<>();
    private Long id = 1L;

    @Override
    public SelfIntro save(final SelfIntro selfIntro) {
        SelfIntro savedSelfIntro = SelfIntro.builder()
                .id(id)
                .memberId(selfIntro.getMemberId())
                .content(selfIntro.getContent())
                .build();
        map.put(id++, savedSelfIntro);

        return savedSelfIntro;
    }

    @Override
    public Optional<SelfIntro> findById(final Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void deleteById(final Long id) {
        map.remove(id);
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
        List<SelfIntroResponse> introResponses = map.values()
                .stream()
                .filter(selfIntro -> isBetween(minAge, maxAge))
                .filter(selfIntro -> isBelongTo(cities))
                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
                .limit(SIZE_PER_PAGE)
                .map(selfIntro -> 셀프_소개글_응답(selfIntro.getId(), selfIntro.getContent()))
                .toList();

        return new PageImpl<>(introResponses);
    }

    private boolean isBetween(final int minAge, final int maxAge) {
        return minAge <= AGE && AGE <= maxAge;
    }

    private boolean isBelongTo(final List<String> cities) {
        return cities.contains(CITY);
    }
}
