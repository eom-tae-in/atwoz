package com.atwoz.member.application.member.profile.style;

import com.atwoz.member.application.member.profile.style.dto.StyleResponses;
import com.atwoz.member.domain.member.profile.Style;
import com.atwoz.member.domain.member.profile.StyleRepository;
import com.atwoz.member.exception.exceptions.member.profile.style.StyleNotFoundException;
import com.atwoz.member.infrastructure.member.profile.style.StyleFakeRepository;
import com.atwoz.member.infrastructure.member.profile.style.dto.StyleResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성;
import static com.atwoz.member.fixture.member.dto.response.StyleResponseFixture.스타일_응답_생성_스타일;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleQueryServiceTest {

    private StyleQueryService styleQueryService;
    private StyleRepository styleRepository;

    @BeforeEach
    void setup() {
        styleRepository = new StyleFakeRepository();
        styleQueryService = new StyleQueryService(styleRepository);
    }

    @Nested
    class 스타일_조회 {

        @Test
        void 스타일을_조회한다() {
            // given
            Style savedStyle = styleRepository.save(스타일_생성());
            Long styleId = savedStyle.getId();

            // when
            StyleResponse styleResponse = styleQueryService.findStyle(styleId);

            // then
            StyleResponse expectedStyleResponse = 스타일_응답_생성_스타일(savedStyle);
            assertThat(styleResponse).usingRecursiveComparison()
                    .isEqualTo(expectedStyleResponse);
        }

        @Test
        void 없는_스타일을_조회할_경우_예외가_발생한다() {
            // given
            Long invalidStyleId = 1L;

            // when & then
            assertThatThrownBy(() -> styleQueryService.findStyle(invalidStyleId))
                    .isInstanceOf(StyleNotFoundException.class);
        }
    }

    @Test
    void 스타일을_페이징으로_조회한다() {
        // given
        Style savedStyle = styleRepository.save(스타일_생성());
        Pageable pageable = PageRequest.of(0, 10);

        // when
        StyleResponses styleResponses = styleQueryService.findStylesWithPaging(pageable);

        // then
        assertSoftly(softly -> {
            softly.assertThat(styleResponses.nowPage()).isEqualTo(0);
            softly.assertThat(styleResponses.nextPage()).isEqualTo(-1);
            softly.assertThat(styleResponses.totalPages()).isEqualTo(1);
            softly.assertThat(styleResponses.totalElements()).isEqualTo(1);
            StyleResponse styleResponse = styleResponses.styleResponses().get(0);
            StyleResponse expectedStyleResponse = 스타일_응답_생성_스타일(savedStyle);
            softly.assertThat(styleResponse)
                    .usingRecursiveComparison()
                    .isEqualTo(expectedStyleResponse);
        });
    }
}
