package com.atwoz.mission.application.mission;

import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.mission.exceptions.MissionNotFoundException;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MissionQueryServiceTest {

    private MissionQueryService missionQueryService;
    private MissionRepository missionRepository;

    @BeforeEach
    void setup() {
        missionRepository = new MissionFakeRepository();
        missionQueryService = new MissionQueryService(missionRepository);
    }

    @Test
    void 미션을_조회한다() {
        // given
        Mission mission = 미션_생성_리워드_100_데일리_공개();
        missionRepository.save(mission);

        // when
        Mission result = missionQueryService.findMissionDetail(mission.getId());

        // then
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(mission);
    }

    @Test
    void 미션이_없다면_조회하지_못한다() {
        // when & then
        assertThatThrownBy(() -> missionQueryService.findMissionDetail(-1L))
                .isInstanceOf(MissionNotFoundException.class);
    }
}
