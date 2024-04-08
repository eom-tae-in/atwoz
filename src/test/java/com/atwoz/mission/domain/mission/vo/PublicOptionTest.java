package com.atwoz.mission.domain.mission.vo;

import com.atwoz.mission.exception.mission.exceptions.PublicOptionInvalidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class PublicOptionTest {

    @Test
    void 미션의_공개_범위가_올바르지_않은_값이라면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> PublicOption.from("invalid"))
                .isInstanceOf(PublicOptionInvalidException.class);
    }
}
