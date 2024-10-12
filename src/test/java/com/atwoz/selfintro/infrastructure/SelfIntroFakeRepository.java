package com.atwoz.selfintro.infrastructure;

import com.atwoz.selfintro.application.dto.SelfIntroFilterRequest;
import com.atwoz.selfintro.domain.SelfIntro;
import com.atwoz.selfintro.domain.SelfIntroRepository;
import com.atwoz.selfintro.infrastructure.dto.SelfIntroQueryResponse;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static com.atwoz.selfintro.fixture.셀프소개글_응답_픽스처.셀프소개글_페이징_조회_응답_픽스처.셀프소개글_응답_셀프소개글아이디_내용;

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
    public Page<SelfIntroQueryResponse> findAllSelfIntrosWithPagingAndFiltering(
            final Pageable pageable,
            final SelfIntroFilterRequest request,
            final Long memberId
    ) {
        List<SelfIntroQueryResponse> introResponses = map.values()
                .stream()
                .filter(selfIntro -> isBetween(request.minAge(), request.maxAge()))
                .filter(selfIntro -> isBelongTo(request.cities()))
                .sorted(Comparator.comparing(SelfIntro::getCreatedAt).reversed())
                .limit(SIZE_PER_PAGE)
                .map(selfIntro -> 셀프소개글_응답_셀프소개글아이디_내용(selfIntro.getId(), selfIntro.getContent()))
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
