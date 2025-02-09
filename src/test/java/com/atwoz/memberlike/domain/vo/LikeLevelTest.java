package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeLevelNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeLevelTest {

    @Test
    void 없는_호감_타입이면_예외가_발생한다() {
        // given
        String name = "abc";

        // when & then
        assertThatThrownBy(() -> LikeLevel.findByName(name))
                .isInstanceOf(LikeLevelNotFoundException.class);
    }
}
