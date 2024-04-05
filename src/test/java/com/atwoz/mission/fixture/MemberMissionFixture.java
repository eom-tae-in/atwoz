package com.atwoz.mission.fixture;

import com.atwoz.mission.domain.membermission.MemberMission;

import java.time.LocalDateTime;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_200_챌린지_공개;

public class MemberMissionFixture {

    public static MemberMission 멤버_미션_생성_완료_보상_수령_안함_데일리() {
        return MemberMission.builder()
                .id(1L)
                .doesGetReward(false)
                .mission(미션_생성_리워드_100_데일리_공개())
                .build();
    }

    public static MemberMission 멤버_미션_생성_완료_보상_수령_완료_데일리() {
        return MemberMission.builder()
                .id(1L)
                .doesGetReward(true)
                .mission(미션_생성_리워드_100_데일리_공개())
                .build();
    }

    public static MemberMission 멤버_미션_생성_완료_보상_안함_데일리_id_시간있음(final long id) {
        return MemberMission.builder()
                .id(id)
                .doesGetReward(false)
                .mission(미션_생성_리워드_100_데일리_공개())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberMission 멤버_미션_생성_완료_보상_수령_안함_챌린지() {
        return MemberMission.builder()
                .id(1L)
                .doesGetReward(false)
                .mission(미션_생성_리워드_200_챌린지_공개())
                .build();
    }
}
