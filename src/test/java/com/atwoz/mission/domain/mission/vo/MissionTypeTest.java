package com.atwoz.mission.domain.mission.vo;

import com.atwoz.mission.exception.mission.exceptions.MissionTypeInvalidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MissionTypeTest {

    @Test
    void 미션명이_올바르지_않으면_예외를_발생시킨다() {
        // when & then
        assertThatThrownBy(() -> MissionType.from("invalid"))
                .isInstanceOf(MissionTypeInvalidException.class);
    }
}
