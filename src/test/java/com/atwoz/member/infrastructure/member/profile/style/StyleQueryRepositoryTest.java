package com.atwoz.member.infrastructure.member.profile.style;

import com.atwoz.helper.IntegrationHelper;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.infrastructure.member.profile.style.dto.StylePagingResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성_이름_코드;
import static com.atwoz.member.fixture.member.dto.response.style.스타일_응답_픽스처.스타일_페이징_조회_응답_픽스처.스타일_페이징_조회_응답_생성_스타일;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleQueryRepositoryTest extends IntegrationHelper {

    @Autowired
    private StyleRepository styleRepository;

    @Autowired
    private StyleQueryRepository styleQueryRepository;

    private List<Style> styles;

    @BeforeEach
    void setup() {
        styles = 스타일_목록_저장();
    }

    @Test
    void 취미를_페이징해서_조회한다() {
        // given
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<StylePagingResponse> result = styleQueryRepository.findStylesWithPaging(pageable);

        // then
        List<StylePagingResponse> stylePagingRespons = result.getContent();
        assertSoftly(softly -> {
            softly.assertThat(result.hasNext()).isTrue();
            softly.assertThat(result.getTotalElements()).isEqualTo(15);
            softly.assertThat(result.getTotalPages()).isEqualTo(2);
            softly.assertThat(result.getNumber()).isEqualTo(0);
            softly.assertThat(result).hasSize(10);
            IntStream.range(0, 10)
                    .forEach(index -> {
                        StylePagingResponse stylePagingResponse = stylePagingRespons.get(index);
                        softly.assertThat(stylePagingResponse)
                                .usingRecursiveComparison()
                                .isEqualTo(스타일_페이징_조회_응답_생성_스타일(styles.get(index)));
                    });
        });
    }

    private List<Style> 스타일_목록_저장() {
        List<Style> savedStyles = new ArrayList<>();
        IntStream.range(0, 15)
                .forEach(index -> {
                    Style style = 스타일_생성_이름_코드("style" + index, "code" + index);
                    styleRepository.save(style);
                    savedStyles.add(style);
                });

        return savedStyles;
    }
}
