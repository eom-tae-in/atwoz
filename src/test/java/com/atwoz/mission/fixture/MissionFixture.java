package com.atwoz.mission.fixture;

import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.vo.MissionType;
import com.atwoz.mission.domain.mission.vo.PublicOption;

public class MissionFixture {

    public static Mission 미션_생성_리워드_100_데일리_공개() {
        return Mission.builder()
                .id(1L)
                .title("mission")
                .reward(100)
                .missionType(MissionType.DAILY)
                .publicOption(PublicOption.PUBLIC)
                .build();
    }
}
