package com.atwoz.memberlike.domain.vo;

import com.atwoz.memberlike.exception.exceptions.LikeIconNotFoundException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class LikeIconTest {

    @Test
    void 없는_호감_아이콘이면_예외가_발생한다() {
        // given
        String name = "abc";

        // when & then
        assertThatThrownBy(() -> LikeIcon.findByName(name))
                .isInstanceOf(LikeIconNotFoundException.class);
    }
}
