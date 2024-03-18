package com.atwoz.mission.fixture;

import com.atwoz.mission.domain.membermission.MemberMission;

import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;

public class MemberMissionFixture {

    public static MemberMission 멤버_미션_생성_진행중() {
        return MemberMission.builder()
                .id(1L)
                .isStatusClear(false)
                .mission(미션_생성_리워드_100_데일리_공개())
                .build();
    }

    public static MemberMission 멤버_미션_생성_완료_보상_수령_안함() {
        return MemberMission.builder()
                .id(1L)
                .isStatusClear(true)
                .doesGetReward(false)
                .mission(미션_생성_리워드_100_데일리_공개())
                .build();
    }

    public static MemberMission 멤버_미션_생성_완료_보상_수령_완료() {
        return MemberMission.builder()
                .id(1L)
                .isStatusClear(true)
                .doesGetReward(true)
                .mission(미션_생성_리워드_100_데일리_공개())
                .build();
    }
}
