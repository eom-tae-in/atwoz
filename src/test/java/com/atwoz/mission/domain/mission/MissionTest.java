package com.atwoz.mission.domain.mission;

import com.atwoz.mission.domain.mission.vo.MissionType;
import com.atwoz.mission.domain.mission.vo.PublicOption;
import com.atwoz.mission.exception.mission.exceptions.RewardValueInvalidException;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MissionTest {

    @Test
    void 리워드가_0보다_작다면_예외를_발생시킨다() {
        // given
        Integer invalidReward = -1;

        // when & then
        assertThatThrownBy(() -> Mission.createDefaultRule("title", invalidReward, MissionType.CHALLENGE.getName(), PublicOption.PUBLIC.getName()))
                .isInstanceOf(RewardValueInvalidException.class);
    }

    @Test
    void id가_같아서_미션이_같다면_true를_반환한다() {
        // given
        Mission mission = 미션_생성_리워드_100_데일리_공개();
        Long id = mission.getId();

        // when
        boolean result = mission.isSameMission(id);

        // then
        assertThat(result).isTrue();
    }
}
