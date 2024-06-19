package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeTypeNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeTypeTest {

    @Test
    void 없는_호감_타입이면_예외가_발생한다() {
        // given
        String name = "abc";

        // when & then
        assertThatThrownBy(() -> LikeType.findByName(name))
                .isInstanceOf(LikeTypeNotFoundException.class);
    }
}
