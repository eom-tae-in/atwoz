package com.atwoz.mission.application.mission;

import com.atwoz.mission.application.mission.dto.MissionCreateRequest;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.mission.exceptions.MissionTypeInvalidException;
import com.atwoz.mission.exception.mission.exceptions.PublicOptionInvalidException;
import com.atwoz.mission.exception.mission.exceptions.RewardValueInvalidException;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MissionServiceTest {

    private MissionService missionService;
    private MissionRepository missionRepository;

    @BeforeEach
    void setup() {
        missionRepository = new MissionFakeRepository();
        missionService = new MissionService(missionRepository);
    }

    @Nested
    class 미션_생성 {

        @Test
        void 미션을_생성한다() {
            // when
            Long result = missionService.createNewMission(new MissionCreateRequest("title", "daily", 100, "public"));

            // then
            assertThat(result).isEqualTo(1L);
        }

        @Test
        void 미션_타입이_올바르지_않다면_예외를_발생시킨다() {
            // when & then
            assertThatThrownBy(() -> missionService.createNewMission(new MissionCreateRequest("title", "123123", 100, "public")))
                    .isInstanceOf(MissionTypeInvalidException.class);
        }

        @Test
        void 공개_타입이_올바르지_않다면_예외를_발생시킨다() {
            // when & then
            assertThatThrownBy(() -> missionService.createNewMission(new MissionCreateRequest("title", "daily", 100, "123123")))
                    .isInstanceOf(PublicOptionInvalidException.class);
        }

        @Test
        void 리워드가_0보다_작으면_예외를_발생시킨다() {
            // when & then
            assertThatThrownBy(() -> missionService.createNewMission(new MissionCreateRequest("title", "daily", -1, "public")))
                    .isInstanceOf(RewardValueInvalidException.class);
        }
    }

    @Test
    void 미션을_제거한다() {
        // given
        Mission savedMission = missionRepository.save(미션_생성_리워드_100_데일리_공개());

        // when & then
        assertDoesNotThrow(() -> missionService.deleteMissionById(savedMission.getId()));
    }
}
