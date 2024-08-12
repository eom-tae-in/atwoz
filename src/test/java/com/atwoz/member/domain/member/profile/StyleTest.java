package com.atwoz.member.domain.member.profile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.atwoz.member.fixture.member.domain.StyleFixture.스타일_생성;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class StyleTest {

    private Style style;

    @BeforeEach
    void setup() {
        style = 스타일_생성();
    }

    @Test
    void 이름과_코드_정보로_스타일을_생성한다() {
        // given
        String styleName = "style1";
        String styleCode = "code1";

        // when
        Style createdStyle = Style.createWith(styleName, styleCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(createdStyle.getName())
                    .isEqualTo(styleName);
            softly.assertThat(createdStyle.getCode())
                    .isEqualTo(styleCode);
        });
    }

    @Test
    void 스타일의_이름과_코드를_업데이트한다() {
        // given
        String updateStyleName = "style2";
        String updateStyleCode = "code2";

        // when
        style.update(updateStyleName, updateStyleCode);

        // then
        assertSoftly(softly -> {
            softly.assertThat(style.getName())
                    .isEqualTo(updateStyleName);
            softly.assertThat(style.getCode())
                    .isEqualTo(updateStyleCode);
        });
    }

    @Test
    void 스타일_정보가_일치하면_true를_반환한다() {
        // given
        Style newStyle = 스타일_생성();
        
        // when
        boolean result = style.isSame(newStyle);

        // then
        assertThat(result).isTrue();
    }
}
