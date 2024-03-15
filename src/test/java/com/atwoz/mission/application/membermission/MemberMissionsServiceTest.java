package com.atwoz.mission.application.membermission;

import com.atwoz.mission.domain.membermission.MemberMission;
import com.atwoz.mission.domain.membermission.MemberMissions;
import com.atwoz.mission.domain.membermission.MemberMissionsRepository;
import com.atwoz.mission.domain.mission.Mission;
import com.atwoz.mission.domain.mission.MissionRepository;
import com.atwoz.mission.exception.MissionNotClearException;
import com.atwoz.mission.exception.MissionNotFoundException;
import com.atwoz.mission.exception.membermission.exceptions.MemberMissionsNotFoundException;
import com.atwoz.mission.intrastructure.membermission.MemberMissionsFakeRepository;
import com.atwoz.mission.intrastructure.mission.MissionFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_완료;
import static com.atwoz.mission.fixture.MemberMissionFixture.멤버_미션_생성_진행중;
import static com.atwoz.mission.fixture.MemberMissionsFixture.멤버_미션들_생성;
import static com.atwoz.mission.fixture.MissionFixture.미션_생성_리워드_100_데일리_공개;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class MemberMissionsServiceTest {

    private MemberMissionsService memberMissionsService;
    private MemberMissionsRepository memberMissionsRepository;
    private MissionRepository missionRepository;

    @BeforeEach
    void init() {
        memberMissionsRepository = new MemberMissionsFakeRepository();
        missionRepository = new MissionFakeRepository();
        memberMissionsService = new MemberMissionsService(memberMissionsRepository, missionRepository);
    }

    @Test
    void 회원_미션_목록에_미션을_등록한다() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);

        Mission mission = 미션_생성_리워드_100_데일리_공개();
        Mission saveMission = missionRepository.save(mission);

        Long memberId = memberMissions.getMemberId();
        Long missionId = saveMission.getId();

        // when
        memberMissionsService.addMemberMission(memberId, missionId);

        // then
        assertThat(memberMissions.getMemberMissions()).isNotEmpty();
    }

    @Test
    void 회원_미션_목록애_잘못된_미션을_넣으면_예외가_발생한다() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);

        Long memberId = memberMissions.getMemberId();
        Long missionId = -1L;

        // when & then
        assertThatThrownBy(() -> memberMissionsService.addMemberMission(memberId, missionId))
                .isInstanceOf(MissionNotFoundException.class);
    }

    @Test
    void 회원의_미션_목록에서_특정_미션의_보상을_구한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Long memberId = memberMissions.getMemberId();
        Mission mission = memberMission.getMission();

        // when
        Integer reward = memberMissionsService.getRewardByMissionId(memberId, mission.getId());

        // then
        assertThat(reward).isEqualTo(mission.getReward());
    }

    @Test
    void 회원의_미션_목록에서_없는_미션의_보상을_구하면_예외가_발생한다() {
        // given
        MemberMissions memberMissions = 멤버_미션들_생성();
        memberMissionsRepository.save(memberMissions);

        MemberMission memberMission = 멤버_미션_생성_완료();
        Long memberId = memberMissions.getMemberId();
        Mission mission = memberMission.getMission();

        // when & then
        assertThatThrownBy(() -> memberMissionsService.getRewardByMissionId(memberId, mission.getId()))
                .isInstanceOf(MissionNotFoundException.class);
    }

    @Test
    void 회원의_미션_목록에서_완료되지_않은_미션의_보상을_구하면_예외가_발생한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_진행중();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Long memberId = memberMissions.getMemberId();
        Mission mission = memberMission.getMission();

        // when & then
        assertThatThrownBy(() -> memberMissionsService.getRewardByMissionId(memberId, mission.getId()))
                .isInstanceOf(MissionNotClearException.class);
    }

    @Test
    void 회원의_미션_목록에서_완료된_미션의_보상_총합을_구한다() {
        // given
        MemberMission memberMission = 멤버_미션_생성_완료();
        MemberMissions memberMissions = 멤버_미션들_생성(memberMission);
        memberMissionsRepository.save(memberMissions);

        Long memberId = memberMissions.getMemberId();

        // when
        Integer reward = memberMissionsService.getAllClearMissionsRewards(memberId);

        // then
        assertThat(reward).isEqualTo(memberMission.getReward());
    }

    @Test
    void 회원의_미션_목록이_없을_때_완료된_미션의_보상_총합을_구하면_예외가_발생한다() {
        // given
        Long memberId = 1L;

        // when & then
        assertThatThrownBy(() -> memberMissionsService.getAllClearMissionsRewards(memberId))
                .isInstanceOf(MemberMissionsNotFoundException.class);
    }
}
